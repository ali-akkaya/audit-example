package me.aliakkaya.auditexample.managers;

import lombok.AllArgsConstructor;
import me.aliakkaya.auditexample.model.LDAPUser;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapClient;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

@AllArgsConstructor
@Service
public class LDAPService {

  private final LdapClient ldapClient;

    private class LDAPUserAttributeMapper implements AttributesMapper<LDAPUser> {
        public LDAPUser mapFromAttributes(Attributes attrs) throws NamingException {
            Attribute samAccountNameAttr = attrs.get("sAMAccountName");
            Attribute mailAttr = attrs.get("mail");
            Attribute cnAttr = attrs.get("cn");
            Attribute nameAttr = attrs.get("givenName");
            Attribute surnameAttr = attrs.get("sn");
            Attribute sicilAttr = attrs.get("extensionattribute3");
            if (samAccountNameAttr != null && mailAttr != null && cnAttr != null && sicilAttr != null) {
                LDAPUser person = new LDAPUser();
                person.setUsername((String) samAccountNameAttr.get());
                person.setMail((String) mailAttr.get());
                person.setNameSurname((String) cnAttr.get());
                person.setName((String) nameAttr.get());
                person.setSurname((String) surnameAttr.get());
                person.setSicil(Long.parseLong((String) sicilAttr.get()));
                return person;
            }
            return null;
        }
    }


    public LDAPUser findLDAPUserBySicilNo(Long sicilNo){

        return ldapClient.search().query(q -> q.where("extensionattribute3").is(String.valueOf(sicilNo))).toList(new LDAPUserAttributeMapper()).get(0);
    }
}
