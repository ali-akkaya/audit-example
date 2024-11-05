package me.aliakkaya.auditexample.managers;

import lombok.AllArgsConstructor;
import me.aliakkaya.auditexample.model.LDAPUser;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapClient;
import org.springframework.stereotype.Service;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import java.util.List;

import static me.aliakkaya.auditexample.converter.NamingEnumerationListConverter.convertNamingEnumerationToList;

@AllArgsConstructor
@Service
public class LDAPService {

  private final LdapClient ldapClient;

    private static class LDAPUserAttributeMapper implements AttributesMapper<LDAPUser> {
        public LDAPUser mapFromAttributes(Attributes attrs) throws NamingException {
            Attribute samAccountNameAttr = attrs.get("sAMAccountName");
            Attribute mailAttr = attrs.get("mail");
            Attribute cnAttr = attrs.get("cn");
            Attribute nameAttr = attrs.get("givenName");
            Attribute surnameAttr = attrs.get("sn");
            Attribute sicilAttr = attrs.get("extensionattribute3");
            List<String> memberOfAttr =  convertNamingEnumerationToList(attrs.get("memberof").getAll()).stream().map((group)->{
                String groupName;
                String[] parts = group.toString().split(",");
                groupName = parts[0].substring(3).trim();
                return groupName;
            }).toList();
            if (samAccountNameAttr != null && mailAttr != null && cnAttr != null && sicilAttr != null) {
                LDAPUser person = new LDAPUser();
                person.setUsername((String) samAccountNameAttr.get());
                person.setMail((String) mailAttr.get());
                person.setNameSurname((String) cnAttr.get());
                person.setName((String) nameAttr.get());
                person.setSurname((String) surnameAttr.get());
                person.setRegisterNumber(Long.parseLong((String) sicilAttr.get()));
                person.setGroups(memberOfAttr);
                return person;
            }
            return null;
        }
    }


    public LDAPUser findLDAPUserByRegisterNo(Long registerNo){

        return ldapClient.search().query(q -> q.where("extensionattribute3").is(String.valueOf(registerNo))).toList(new LDAPUserAttributeMapper()).get(0);
    }
}
