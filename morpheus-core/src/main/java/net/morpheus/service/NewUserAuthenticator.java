package net.morpheus.service;

import net.morpheus.exception.UserAlreadyExistsException;
import net.morpheus.exception.UserNotInCauthException;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.ldap.core.LdapTemplate;

public class NewUserAuthenticator {

    public static final String DN_SEARCH_BASE = "uid=%s,ou=People,dc=isp,dc=company,dc=com";
    private LdapTemplate ldapTemplate;
    private EmployeeRepository employeeRepository;

    public NewUserAuthenticator(LdapTemplate ldapTemplate, EmployeeRepository employeeRepository) {
        this.ldapTemplate = ldapTemplate;
        this.employeeRepository = employeeRepository;
    }

    public void validateUserCanBeCreated(String username) {
        try {
            ldapTemplate.list(String.format(DN_SEARCH_BASE, username));
        } catch (Exception e) {
            throw new UserNotInCauthException(username);
        }
        if (employeeRepository.findByName(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }
    }

}
