package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/clients", "/api/login", "/api/logout").permitAll()
                .antMatchers("/web/pages/index.html","/web/js/**","/web/accountStile/estilo2.css","/web/image/**", "/api/loans").permitAll()
                .antMatchers("/manager/manager.html","/manager/manager.js","/api/clients","/rest/**","/h2-console/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/web/accountStile/estilo2.css","/api/clients/current/**", "/api/clients/accounts", "/api/clients/current{id}","/api/clients/accounts/**","/api/clients/current/accounts").hasAuthority("Cliente")
                .antMatchers(HttpMethod.POST, "/api/clients/current/**","/api/clients/current/accounts", "/api/clients/current/cards").hasAuthority("Cliente")
                .antMatchers(HttpMethod.PATCH,  "/api/clients/current/cards/deactivate").hasAuthority("Cliente")
                .antMatchers("/web/**").hasAnyAuthority("Cliente")
                .anyRequest().denyAll();
        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }


}
