
package com.example.cgpaplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.cgpaplatform.security.JwtAuthenticationFilter;
import com.example.cgpaplatform.security.StudentAuthorizationInterceptor;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final StudentAuthorizationInterceptor studentAuthorizationInterceptor;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            StudentAuthorizationInterceptor studentAuthorizationInterceptor) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.studentAuthorizationInterceptor = studentAuthorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(studentAuthorizationInterceptor)
                .addPathPatterns("/api/students/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                )
                .accessDeniedHandler((request, response, exception) ->
                    response.setStatus(HttpStatus.FORBIDDEN.value())
                )
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                // Only ADMIN can create student records
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/students"
                ).hasRole("ADMIN")

                // Only ADMIN can view all students
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/students"
                ).hasRole("ADMIN")

                // Other authenticated requests
                .anyRequest().authenticated()
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
