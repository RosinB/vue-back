package com.aic.edudemo.vuebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ 關閉 CSRF（如果使用 JWT）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").permitAll() // ✅ 允許 `/users/**` API 被 API Gateway 訪問
                        .anyRequest().authenticated() // 🔒 其他 API 仍需驗證
                )
                .formLogin(form -> form.disable()) // ❌ 停用 `/login` 自動跳轉
                .httpBasic(httpBasic -> httpBasic.disable()); // ❌ 停用 HTTP Basic 驗證

        return http.build();
    }
}
