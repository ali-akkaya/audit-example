package me.aliakkaya.auditexample.model;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LDAPUser implements UserDetails {
    private String username;

    private String password;

    private String mail;

    private Boolean enabled;

    private String name;

    private String surname;
    private String nameSurname;
    private Long registerNumber;
    private List<String> groups;

    @Transient
    private Collection<GrantedAuthority> authorities;

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}
