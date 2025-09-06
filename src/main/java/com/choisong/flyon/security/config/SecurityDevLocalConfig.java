package com.choisong.flyon.security.config;

import com.choisong.flyon.security.filter.JwtAuthenticationEntryPoint;
import com.choisong.flyon.security.filter.JwtAuthenticationFilter;
import com.choisong.flyon.security.filter.JwtExceptionHandlingFilter;
import com.choisong.flyon.security.scanner.NoAuthRequiredAnnotationScanner;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@Profile({"dev", "local"})
public class SecurityDevLocalConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionHandlingFilter jwtExceptionHandlingFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final NoAuthRequiredAnnotationScanner scanner;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                request ->
//                    request.requestMatchers(scanner.getPublicUrls().toArray(new String[0]))
                    request.requestMatchers("/api/v3/api-docs/**", "/api/swagger-ui/**", "/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
            .logout(AbstractHttpConfigurer::disable)
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(
                jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionHandlingFilter, JwtAuthenticationFilter.class)
            .exceptionHandling(
                e ->
                    e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
            List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader(AUTHORIZATION_HEADER);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}