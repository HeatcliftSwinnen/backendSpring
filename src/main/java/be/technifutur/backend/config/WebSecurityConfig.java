package be.technifutur.backend.config;

import be.technifutur.backend.jwt.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTFilter jwtFilter) throws Exception {

        http.csrf( AbstractHttpConfigurer::disable )
                .httpBasic( AbstractHttpConfigurer::disable );

        http.sessionManagement( sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );

        http.addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class );

        http.authorizeHttpRequests(
                registry -> registry
                        .requestMatchers("/swagger/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        //User-Controller
                        .requestMatchers(HttpMethod.GET , "/user/*").authenticated()
                        .requestMatchers(HttpMethod.PUT , "/user/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH , "/user/*/role").permitAll()
                        .requestMatchers(HttpMethod.PATCH , "/user/*/money").authenticated()
                        .requestMatchers(HttpMethod.PATCH , "/user/*/coin").authenticated()
                        .requestMatchers(HttpMethod.PATCH , "/user/wishlist/add").authenticated()
                        .requestMatchers(HttpMethod.PATCH , "/user/library/offer").authenticated()
                        .requestMatchers(HttpMethod.PATCH , "/user/library/buy").authenticated()
                        .requestMatchers(HttpMethod.PATCH , "/user/friendlist/delete").authenticated()
                        .requestMatchers(HttpMethod.PATCH , "/user/friendlist/add").authenticated()
                        .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET , "/user/wishlist").authenticated()
                        .requestMatchers(HttpMethod.GET , "/user/wishlist").authenticated()
                        .requestMatchers(HttpMethod.DELETE , "/user/wishlist/delete").authenticated()

                        //Studio-Controller
                        .requestMatchers(HttpMethod.GET , "/studio/*").permitAll()
                        .requestMatchers(HttpMethod.PUT , "/studio/*").hasAnyRole("STUDIO", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/studio/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/studio" ).permitAll()
                        .requestMatchers(HttpMethod.POST , "/studio").hasAnyRole("STUDIO", "ADMIN")

                        //Loan-request
                        .requestMatchers("/loanRequest/**").permitAll()

                        //Game-Controller
                        .requestMatchers(HttpMethod.GET , "/game/*").permitAll()
                        .requestMatchers(HttpMethod.PUT , "/game/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/game/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/game" ).permitAll()
                        .requestMatchers(HttpMethod.POST , "/game").hasAnyRole("STUDIO", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH  , "/game/*/money").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH  , "/game/*/imageUrl").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH  , "/game/*/coin").hasRole("ADMIN")

                        //Auth-Controller
                        .requestMatchers(HttpMethod.POST , "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST ,  "/auth/register" ).permitAll()
                        .requestMatchers(HttpMethod.PATCH ,"auth/password").authenticated()

                        //Examples: /** veut dire tout les liens
//                        .requestMatchers( "/auth/login",  "/auth/register" ).permitAll()
//                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/**").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/**").hasRole("ADMIN")
//                        .requestMatchers("/**").authenticated()
        );
        return http.build();
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}