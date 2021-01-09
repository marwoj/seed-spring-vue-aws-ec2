package pl.seed.core

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebFluxSecurity
internal class SecurityConfig(
    @Value("\${seed.cors-allowed-origin}") private val allowedOrigin: String,
    @Value("\${seed.basicAuthUsername}") private val username: String,
    @Value("\${seed.basicAuthPassword}") private val password: String
) {

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/public/**").permitAll()
            .anyExchange().authenticated()
            .and().httpBasic()
            .and().cors().configurationSource(corsConfiguration())
            .and().formLogin().disable()
            .build()
    }

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val user: UserDetails = User.builder()
            .username(username)
            .password("{noop}$password")
            .roles("USER")
            .build()
        return MapReactiveUserDetailsService(user)
    }

    private fun corsConfiguration(): UrlBasedCorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin(allowedOrigin)
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }
}
