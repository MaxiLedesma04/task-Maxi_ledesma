package com.mindhub.homebanking.configurations;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity

@Configuration
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests (authorizeRequests ->
                        authorizeRequests
                .requestMatchers(HttpMethod.POST, "/api/clients", "/api/login", "/api/logout", "/api/transaction/payment").permitAll()
                .requestMatchers("/web/pages/index.html","/web/js/**","/web/accountStile/estilo2.css","/web/image/**", "/api/loans", "/otro.html", "/otro.css").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/create/loans").hasAuthority("ADMIN")
                .requestMatchers("/manager/manager.html","/manager/manager.js","/api/clients","/rest/**","/h2-console/**", "/api/create/loans","/manager/createLoan.html", "/manager/managerjs/createLoan.js", "/manager/managerJs/createLoan.js").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/web/accountStile/estilo2.css","/api/clients/current/**", "/api/clients/accounts", "/api/clients/current{id}","/api/clients/accounts/**","/api/clients/current/accounts", "/api/transactions").hasAuthority("Client")
                .requestMatchers(HttpMethod.POST, "/api/clients/current/accounts", "/api/clients/current/cards","/api/transactions", "/api/clients/current/loans/loanPayment", "/api/loans").hasAuthority("Client")
                .requestMatchers(HttpMethod.PATCH,  "/api/clients/current/cards/deactivate", "/api/clients/current/loans/loanPayment", "/api/clients/current/accounts/deactivate").hasAuthority("Client")
                .requestMatchers("/web/**").hasAnyAuthority("Client"));
        http.formLogin(cfg -> cfg
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/api/login")
                .failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .defaultSuccessUrl("/web/pages/accounts.html", true)
                .permitAll()
        );
        http.logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/web/pages/index.html")
                .logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext())
        );
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
