package me.aliakkaya.auditexample.config;

import lombok.NonNull;
import me.aliakkaya.auditexample.model.LDAPUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration(value = "auditorAware")
public class AuditorAwareImp implements AuditorAware<String> {
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        // Can use Spring Security to return currently logged-in user
         return ((LDAPUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername().describeConstable();
    }
}