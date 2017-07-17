package org.ng.undp.vdms.configs;

/**
 * Created by abdulhakim on 10/12/16.
 */

import org.ng.undp.vdms.configs.security.CustomPermissionEvaluator;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private UserService userDetailsService;


    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    FailureLoginHandler authenticationFailureHandler() {
        return new FailureLoginHandler();
    }

    @Bean
    CustomLoginSuccessHandler authenticationSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(User.PASSWORD_ENCODER);
    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/bundles/**", "/images/**", "vmsb/**", "/public/**", "/css/**", "images/**", "css/**", "js/**", "/fonts/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().cacheControl().disable()
                .and()

                .authorizeRequests().antMatchers("/terms","/contact", "/accounts/login", "/", "/notices/{uuid}", "/notices/public", "/home/faqs", "/register", "/api/**", "/accounts/forgotpassword", "/accounts/resetpassword", "/accounts/resetpassword**", "/accounts/changeMyPassword", "/accounts/activate", "/admin/**")

                .permitAll()

                .anyRequest().authenticated()
                .and()

                .formLogin()


                //.defaultSuccessUrl("/accounts", true)


                .loginPage("/accounts/login")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .and()
                .csrf().disable()



                .rememberMe()
                .tokenValiditySeconds(1209600)
                .and()
               // .httpBasic()
               // .and()

                .logout().logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()

                .permitAll()


                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).and().headers().cacheControl();

        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request,
                                 HttpServletResponse response, AuthenticationException authException)
                    throws IOException, ServletException {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


               String errorMessage = "You must be loggedin to access that section. <a href='/contact'> Contact Support</a>";

               redirectStrategy.sendRedirect(request, response,"/accounts/login?error=" + errorMessage);
            }
        });
/*
        http.sessionManagement()
                .maximumSessions(3)
                .sessionRegistry(sessionRegistry());*/


        http.sessionManagement()
                .maximumSessions(-1)
                .sessionRegistry(sessionRegistry());
    }


}