package org.glacier.pricingapplication.security;

import org.glacier.pricingapplication.service.PricingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails superUser = User.withUsername("superUser")
                .password("{bcrypt}$2a$12$HluQHbdiahX8DTak8acwWuL7CcjYz6afucaujc02b6K1lvaFg4Wpq")
                .roles("USER", "ADMIN")
                .build();

        UserDetails regularUser = User.withUsername("regularUser")
                .password("{bcrypt}$2a$12$vQLDhWWSp1JbEhTeJP9kS.vDziQHFKKWguKGFInrx1g3xoNjAcwwW")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(superUser, regularUser);
    }

    @Bean
    public SecurityFilterChain pricingTrackFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                auth.requestMatchers(HttpMethod.GET, "/api/v1/pricing/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/pricing/**").hasRole("ADMIN")
                        .anyRequest().denyAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
