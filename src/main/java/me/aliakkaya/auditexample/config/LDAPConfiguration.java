package me.aliakkaya.auditexample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapClient;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LDAPConfiguration {
    @Value("${ldap.url}")
    private String LDAP_URL;
    @Value("${ldap.client.user}")
    private String LDAP_CLIENT_USER;

    @Value("${ldap.client.password}")
    private String LDAP_LDAP_CLIENT_PASSWORD;
    @Value("${ldap.root-dn}")
    private String LDAP_ROOT_DN;

    @Bean
    public LdapContextSource ldapContext() {
        LdapContextSource ldapContext = new LdapContextSource();
        ldapContext.setUrl(LDAP_URL);
        ldapContext.setBase(LDAP_ROOT_DN);
        ldapContext.setUserDn(LDAP_CLIENT_USER);
        ldapContext.setPassword(LDAP_LDAP_CLIENT_PASSWORD);
        return ldapContext;
    }


    @Bean
    public LdapClient ldapClient(){
        return LdapClient.create(ldapContext());
    }

}
