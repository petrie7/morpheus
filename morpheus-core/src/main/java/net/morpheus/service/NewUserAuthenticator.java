package net.morpheus.service;

import net.morpheus.exception.UserNotInCauthException;
import org.springframework.ldap.core.LdapTemplate;

public class NewUserAuthenticator {

    public static final String DN_SEARCH_BASE = "uid=%s,ou=People,dc=isp,dc=company,dc=com";
    private LdapTemplate ldapTemplate;

    public NewUserAuthenticator(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public void validateUserExistsInCauth(String username) {
        try {
            ldapTemplate.list(String.format(DN_SEARCH_BASE, username));
        } catch (Exception e) {
            throw new UserNotInCauthException(username);
        }
    }

}
