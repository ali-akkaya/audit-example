package me.aliakkaya.auditexample.config;

import me.aliakkaya.auditexample.model.LDAPUser;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.List;

import static me.aliakkaya.auditexample.converter.NamingEnumerationListConverter.convertNamingEnumerationToList;

public class MyLdapUserDetailsMapper extends LdapUserDetailsMapper {
    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
                                          Collection<? extends GrantedAuthority> authorities){
        LdapUserDetails ldapUserDetails = (LdapUserDetails) super.mapUserFromContext(ctx, username, authorities);
        LDAPUser user = new LDAPUser();
        user.setEnabled(ldapUserDetails.isEnabled());
        user.setUsername(ldapUserDetails.getUsername());
        try {
            user.setMail(ctx.getAttributes().get("mail").get().toString());
            user.setName(ctx.getAttributes().get("givenName").get().toString());
            user.setSurname(ctx.getAttributes().get("sn").get().toString());
            user.setNameSurname(ctx.getAttributes().get("cn").get().toString());
            user.setRegisterNumber(Long.valueOf(ctx.getAttributes().get("extensionattribute3").get().toString()));

            List<String> memberOfAttr =  convertNamingEnumerationToList(ctx.getAttributes().get("memberof").getAll()).stream().map((group)->{
                String groupName;
                String[] parts = group.toString().split(",");
                groupName = parts[0].substring(3).trim();
                return groupName;
            }).toList();
            user.setGroups(memberOfAttr);

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
