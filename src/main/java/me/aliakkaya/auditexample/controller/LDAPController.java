package me.aliakkaya.auditexample.controller;

import lombok.AllArgsConstructor;
import me.aliakkaya.auditexample.managers.LDAPService;
import me.aliakkaya.auditexample.model.LDAPUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/ldap")
public class LDAPController {


    private LDAPService ldapService;



    @GetMapping("/user")
    public LDAPUser getUserByRegisterNo(@RequestParam Long registerNo){
      return  ldapService.findLDAPUserByRegisterNo(registerNo);
    }
}
