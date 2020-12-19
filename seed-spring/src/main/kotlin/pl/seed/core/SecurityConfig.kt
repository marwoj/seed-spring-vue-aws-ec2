package pl.seed.core

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
internal class SecurityConfig(
    @Value("\${seed.cors-allowed-origin}") private val allowedOrigin: String,
    @Value("\${seed.basicAuthUsername}") private val username: String,
    @Value("\${seed.basicAuthPassword}") private val password: String
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        auth
            .inMemoryAuthentication()
            .withUser(username).password(encoder.encode(password)).roles("USER")
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .and().authorizeRequests().antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and().httpBasic()
            .and().cors().configurationSource(corsConfiguration())
            .and().csrf().disable()
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
