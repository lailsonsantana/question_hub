package com.example.questifysharedapi.config;

import com.example.questifysharedapi.config.filter.JwtFilter;
import com.example.questifysharedapi.service.JwtService;
import com.example.questifysharedapi.service.UserService;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // Indicate for spring that this class is going do
// the control security from application
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(JwtService jwtService , UserService userService){

        return new JwtFilter(jwtService , userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // A specific implementation
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http , JwtFilter jwtFilter) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/users/**").permitAll();
                    auth.requestMatchers("/api/questions/**").permitAll();
                    //auth.anyRequest().authenticated();
                    auth.anyRequest().permitAll();
                    //.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                })
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedOrigins(List.of("https://questify-shared-project-1.onrender.com","http://localhost:3000"));
        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        // Define what url's might have access this api
        // To our context all url's are enable
        cors.registerCorsConfiguration("/**" , config);

        return cors;
    }
}
