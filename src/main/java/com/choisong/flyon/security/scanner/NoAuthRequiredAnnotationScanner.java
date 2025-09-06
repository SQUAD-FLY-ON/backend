package com.choisong.flyon.security.scanner;

import com.choisong.flyon.security.annotation.NoAuthRequired;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@Getter
public class NoAuthRequiredAnnotationScanner {

    private final List<String> publicUrls = new ArrayList<>();

    public NoAuthRequiredAnnotationScanner(RequestMappingHandlerMapping mapping) {
        for (var entry : mapping.getHandlerMethods().entrySet()) {
            HandlerMethod handler = entry.getValue();
            if (handler.hasMethodAnnotation(NoAuthRequired.class)) {
                Set<String> patterns = entry.getKey().getPatternValues();
                publicUrls.addAll(patterns);
            }
        }
        publicUrls.addAll(List.of("/api/v3/api-docs/**", "/api/swagger-ui/**"));
    }
}
