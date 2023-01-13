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

                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()

//
//                .antMatchers(HttpMethod.GET, "/users").permitAll()
//                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
//                .antMatchers(HttpMethod.PUT, "/users/**").permitAll()
//                .antMatchers(HttpMethod.PATCH, "/users/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/users/**").permitAll()



//antMatchers for recipes......................................................................................
                .antMatchers(HttpMethod.POST, "/recipes").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/recipes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/ingredients").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/ingredients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/instructions").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/instructions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/utensils").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/utensils/**").hasRole("ADMIN")

                .antMatchers("/recipes").hasAnyRole("ADMIN", "USER")
                .antMatchers("/ingredients").hasAnyRole("ADMIN", "USER")
                .antMatchers("/instructions").hasAnyRole("ADMIN", "USER")
                .antMatchers("/utensils").hasAnyRole("ADMIN", "USER")

                .antMatchers("/recipes/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/ingredients/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/instructions/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/utensils/**").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.PUT, "/recipes/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/ingredients/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/instructions/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/utensils/**").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.PATCH, "/recipes/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/ingredients/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/instructions/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/utensils/**").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.POST, "/recipes/**/**").hasAnyRole("ADMIN", "USER")


                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()/*alleen dit punt mag toegankelijk zijn voor niet ingelogde gebruikers*/
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}