package com.htec.vojinpesalj.dakarrally.security;

import com.htec.vojinpesalj.dakarrally.security.jwt.JwtAuthEntryPoint;
import com.htec.vojinpesalj.dakarrally.security.jwt.JwtAuthTokenFilter;
import com.htec.vojinpesalj.dakarrally.security.jwt.JwtProvider;
import com.htec.vojinpesalj.dakarrally.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthEntryPoint unauthorizedHandler;
    private final JwtProvider jwtProvider;

    public WebSecurityConfig(
            UserDetailsServiceImpl userDetailsService,
            JwtAuthEntryPoint unauthorizedHandler,
            JwtProvider jwtProvider) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter(jwtProvider, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/h2-console/**")
                .permitAll()
                .antMatchers(
                        "/api/races/{\\d+}/statistic",
                        "/api/vehicles/{\\d+}/statistic",
                        "/api/races/{\\d+}/vehicles/leaderboard")
                .hasAnyRole("USER", "ADMIN")
                .anyRequest()
                .hasRole("ADMIN")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(
                authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();
    }
}
