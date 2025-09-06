package com.choisong.flyon.global.swagger;

import com.choisong.flyon.global.advice.GlobalExceptionHandler.ErrorResponse;
import com.choisong.flyon.global.advice.GlobalResponseBodyAdvice.SuccessResponse;
import com.choisong.flyon.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

@Configuration
@OpenAPIDefinition(
    info =
    @Info(
        title = "Fly:On API Docs",
        description = "2025 한국관광공사x카카오 관광데이터 공모전",
        version = "v1"))
public class SwaggerConfig {

    private static final String[] statusCode = {"200", "201", "204", "301", "302"};

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = getSecurityScheme();
        SecurityRequirement securityRequirement = getSecurityRequireMent();

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
            .security(List.of(securityRequirement));
    }

    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");
    }

    private SecurityRequirement getSecurityRequireMent() {
        return new SecurityRequirement().addList("bearerAuth");
    }


    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            this.wrapSuccessResponse(operation, SuccessResponse.class, "data");
            this.errorCodeHandle(operation, handlerMethod);
            return operation;
        };
    }

    private void errorCodeHandle(Operation operation, HandlerMethod handlerMethod) {
        ApiErrorCodeExamples apiErrorCodeExamples =
            handlerMethod.getMethodAnnotation(ApiErrorCodeExamples.class);

        if (apiErrorCodeExamples != null) {
            generateErrorCodeResponseExample(operation, apiErrorCodeExamples.value());
        } else {
            ApiErrorCodeExample apiErrorCodeExample =
                handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);
            if (apiErrorCodeExample != null) {
                generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
            }
        }
    }

    private void generateErrorCodeResponseExample(Operation operation, ErrorCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        // ExampleHolder(에러 응답값) 객체를 만들고 에러 코드별로 그룹화
        Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
            Arrays.stream(errorCodes)
                .map(
                    errorCode ->
                        ExampleHolder.builder()
                            .holder(getSwaggerExample(errorCode))
                            .code(errorCode.getHttpStatus().value())
                            .name(errorCode.name())
                            .build())
                .collect(Collectors.groupingBy(ExampleHolder::code));

        // ExampleHolders를 ApiResponses에 추가
        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    // 단일 에러 응답값 예시 추가
    private void generateErrorCodeResponseExample(Operation operation, ErrorCode errorCode) {
        ApiResponses responses = operation.getResponses();

        // ExampleHolder 객체 생성 및 ApiResponses에 추가
        ExampleHolder exampleHolder =
            ExampleHolder.builder()
                .holder(getSwaggerExample(errorCode))
                .code(errorCode.getHttpStatus().value())
                .name(errorCode.name())
                .build();
        addExamplesToResponses(responses, exampleHolder);
    }

    private Example getSwaggerExample(ErrorCode errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        Example example = new Example();
        example.setValue(errorResponse);
        return example;
    }

    private void addExamplesToResponses(
        ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
            (status, v) -> {
                Content content = new Content();
                MediaType mediaType = new MediaType();
                ApiResponse apiResponse = new ApiResponse();

                v.forEach(
                    exampleHolder ->
                        mediaType.addExamples(
                            exampleHolder.name(), exampleHolder.holder()));
                content.addMediaType("application/json", mediaType);
                apiResponse.setContent(content);
                responses.addApiResponse(String.valueOf(status), apiResponse);
            });
    }

    private void addExamplesToResponses(ApiResponses responses, ExampleHolder exampleHolder) {
        Content content = new Content();
        MediaType mediaType = new MediaType();
        ApiResponse apiResponse = new ApiResponse();

        mediaType.addExamples(exampleHolder.name(), exampleHolder.holder());
        content.addMediaType("application/json", mediaType);
        apiResponse.content(content);
        responses.addApiResponse(String.valueOf(exampleHolder.code()), apiResponse);
    }

    private void wrapSuccessResponse(Operation operation, Class<?> type, String wrapFieldName) {
        for (String code : statusCode) {
            ApiResponse apiResponse = operation.getResponses().get(code);
            if (Objects.nonNull(apiResponse)) {
                Content content = apiResponse.getContent();
                wrap(content, type, wrapFieldName, code);
            }
        }
    }

    private void wrap(final Content content, Class<?> type, String wrapFieldName, String code) {
        if (Objects.nonNull(content)) {
            content.forEach(
                (mediaTypeKey, mediaType) -> {
                    Schema<?> originalSchema = mediaType.getSchema();
                    Schema<?> wrappedSchema =
                        wrapSchema(originalSchema, type, wrapFieldName, code);
                    mediaType.setSchema(wrappedSchema);
                });
        }
    }

    @SneakyThrows
    private <T> Schema<T> wrapSchema(
        Schema<?> originalSchema, Class<T> type, String wrapFieldName, String code) {
        final Schema<T> wrapperSchema = new Schema<>();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("httpStatusCode")) {
                wrapperSchema.addProperty(
                    field.getName(), new Schema<>().type("integer").example(code));
            }
            if (field.getName().equals("httpStatusMessage")) {
                String reasonPhrase = HttpStatus.resolve(Integer.parseInt(code)).getReasonPhrase();
                wrapperSchema.addProperty(
                    field.getName(), new Schema<>().type("string").example(reasonPhrase));
            }
            field.setAccessible(false);
        }
        wrapperSchema.addProperty(wrapFieldName, originalSchema);
        return wrapperSchema;
    }
}
