package me.aliakkaya.auditexample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    @Value("${ldap.url}")
    private String LDAP_URL;
    @Value("${ldap.domain.com-tr}")
    private String LDAP_DOMAIN_COM_TR;

    @Value("${ldap.domain.net}")
    private String LDAP_DOMAIN_NET;
    @Value("${ldap.root-dn}")
    private String LDAP_ROOT_DN;

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/authenticate/**").permitAll()
                        .requestMatchers("/person/retrieve/**").permitAll()
                        .anyRequest().authenticated()
                )
                //Couldn't authenticate with basic auth without below configuration.
                //By default, Spring Security’s HTTP Basic Authentication support is enabled.
                // However, as soon as any servlet based configuration is provided, HTTP Basic must be explicitly provided.
                .httpBasic(Customizer.withDefaults())
                //CSRF disabled for POST PUT request. CSRF is more commonly MVC problem, and it is most likely not needed in REST API.
                .csrf((csrf) -> csrf.disable())
                .authenticationProvider(ldapAuthenticationProviderForComTr())
                .authenticationProvider(ldapAuthenticationProviderForNet())
        ;

        return http.build();
    }

    @Bean()
    public ActiveDirectoryLdapAuthenticationProvider ldapAuthenticationProviderForComTr() {
        ActiveDirectoryLdapAuthenticationProvider adProvider = new ActiveDirectoryLdapAuthenticationProvider(LDAP_DOMAIN_COM_TR,
                LDAP_URL, LDAP_ROOT_DN);
        adProvider.setConvertSubErrorCodesToExceptions(true);
        adProvider.setUseAuthenticationRequestCredentials(true);
        adProvider.setUserDetailsContextMapper(new MyLdapUserDetailsMapper());
        return adProvider;
    }

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider ldapAuthenticationProviderForNet() {
        ActiveDirectoryLdapAuthenticationProvider adProvider = new ActiveDirectoryLdapAuthenticationProvider(LDAP_DOMAIN_NET,
                LDAP_URL, LDAP_ROOT_DN);
        adProvider.setConvertSubErrorCodesToExceptions(true);
        adProvider.setUseAuthenticationRequestCredentials(true);
        adProvider.setUserDetailsContextMapper(new MyLdapUserDetailsMapper());
        return adProvider;
    }


}