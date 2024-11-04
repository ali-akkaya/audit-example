package me.aliakkaya.auditexample.controller;

import lombok.AllArgsConstructor;
import me.aliakkaya.auditexample.model.LoginDto;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/authenticate")
public class AuthController {

    private final AuthenticationProvider ldapAuthenticationProviderForComTr;


    private final AuthenticationProvider ldapAuthenticationProviderForNet;



    @PostMapping()
    public boolean authenticate(@RequestBody LoginDto loginCredential){

        Authentication userAuth = getAuthentication(loginCredential.getUserName(), loginCredential.getPassword());
     return    userAuth.isAuthenticated();
    }

    private Authentication getAuthentication(String username, String password) {
        try{
            return ldapAuthenticationProviderForComTr.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch (Exception e){
            return ldapAuthenticationProviderForNet.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
    }
}
