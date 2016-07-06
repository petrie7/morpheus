package net.morpheus.service;

import net.morpheus.domain.Employee;
import net.morpheus.domain.Level;
import net.morpheus.exception.UserNotInCauthException;
import net.morpheus.persistence.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ldap.core.LdapTemplate;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static net.morpheus.service.NewUserAuthenticator.DN_SEARCH_BASE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewUserAuthenticatorTest {

    private LdapTemplate ldapTemplate;
    private NewUserAuthenticator newUserAuthenticator;
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        ldapTemplate = mock(LdapTemplate.class);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        newUserAuthenticator = new NewUserAuthenticator(ldapTemplate, employeeRepository);
    }

    @Test
    public void validatesUserWhoExistsInCauthAndNotAlreadyInSystem() throws Exception {
        String username = "someUsername";
        when(employeeRepository.findByName(username)).thenReturn(emptyList());
        when(ldapTemplate.list(String.format(DN_SEARCH_BASE, username))).thenReturn(emptyList());
        newUserAuthenticator.validateUserCanBeCreated(username);
    }

    @Test(expected = UserNotInCauthException.class)
    public void throwsExceptionWhenUserDoesNotExistInCauth() {
        String username = "someNonExistentUser";
        when(ldapTemplate.list(String.format(DN_SEARCH_BASE, username))).thenThrow(new RuntimeException());
        newUserAuthenticator.validateUserCanBeCreated(username);
    }

    @Test(expected = UserNotInCauthException.class)
    public void throwsExceptionWhenUserExistsInCauthButAlreadyExistsInSystem() {
        String username = "someNonExistentUser";
        when(employeeRepository.findByName(username)).thenReturn(asList(Employee.developer(username, null, Level.JuniorDeveloper)));
        when(ldapTemplate.list(String.format(DN_SEARCH_BASE, username))).thenReturn(emptyList());
        newUserAuthenticator.validateUserCanBeCreated(username);
    }

}