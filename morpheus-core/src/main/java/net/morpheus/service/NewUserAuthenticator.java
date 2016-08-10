package net.morpheus.service;

import net.morpheus.exception.UserAlreadyExistsException;
import net.morpheus.exception.UserNotInCauthException;
import net.morpheus.persistence.EmployeeRecordRepository;
import org.springframework.ldap.core.LdapTemplate;

public class NewUserAuthenticator {

    public static final String DN_SEARCH_BASE = "uid=%s,ou=People,dc=isp,dc=company,dc=com";
    private LdapTemplate ldapTemplate;
    private EmployeeRecordRepository employeeRecordRepository;

    public NewUserAuthenticator(LdapTemplate ldapTemplate, EmployeeRecordRepository employeeRecordRepository) {
        this.ldapTemplate = ldapTemplate;
        this.employeeRecordRepository = employeeRecordRepository;
    }

    public void validateUserCanBeCreated(String username) {
        try {
            ldapTemplate.list(String.format(DN_SEARCH_BASE, username));
        } catch (Exception e) {
            throw new UserNotInCauthException(username);
        }
        if (employeeRecordRepository.findByName(username).size() != 0) {
            throw new UserAlreadyExistsException(username);
        }
    }

}
