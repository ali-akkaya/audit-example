package me.aliakkaya.auditexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())
                //Couldn't authenticate with basic auth without below configuration.
                .httpBasic(Customizer.withDefaults())
                //CSRF disabled for POST PUT request. CSRF is more commonly MVC problem and it is most likely not needed in REST API.
                .csrf((csrf) -> csrf.disable());

        return http.build();
    }


}