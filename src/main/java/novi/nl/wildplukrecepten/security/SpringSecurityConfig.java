package novi.nl.wildplukrecepten.security;


import novi.nl.wildplukrecepten.filter.JwtRequestFilter;
import novi.nl.wildplukrecepten.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /*inject customUserDetailService en jwtRequestFilter*/
    private CustomUserDetailsService customUserDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        //JWT token authentication
        http
                .csrf().disable()
                .httpBasic().disable().cors().and()
                .authorizeRequests()

                // antMatchers for users........................................................................................
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

//                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()

                // antMatchers for recipes
                .antMatchers(HttpMethod.GET, "/recipes").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/utensils").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/ingredients").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/instructions").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.POST, "/recipes").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/ingredients").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST, "/instructions").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/utensils").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.PUT, "/recipes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/ingredients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/instructions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/utensils/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.PATCH, "/recipes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/ingredients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/instructions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/utensils/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.DELETE, "/recipes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/ingredients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/instructions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/utensils/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/recipes/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/ingredients/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/instructions/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/utensils/**").hasAnyRole("ADMIN", "USER")

                // adding a photo to a new recipe
                .antMatchers(HttpMethod.POST, "/recipes/**/**").hasAnyRole("ADMIN", "USER")

                // mail
                .antMatchers("/sendMail").hasRole("ADMIN")
                .antMatchers("/sendMailWithAttachment").hasRole("ADMIN")

                // up- and download
                .antMatchers("/upload").hasAnyRole("ADMIN", "USER")
                .antMatchers("/download").hasAnyRole("ADMIN", "USER")

                // authentication
                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()

//                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}