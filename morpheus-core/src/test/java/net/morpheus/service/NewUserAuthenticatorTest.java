package net.morpheus.service;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;
import net.morpheus.exception.UserNotInCauthException;
import net.morpheus.persistence.mongo.MongoEmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ldap.core.LdapTemplate;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static net.morpheus.service.NewUserAuthenticator.DN_SEARCH_BASE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewUserAuthenticatorTest {

    private LdapTemplate ldapTemplate;
    private NewUserAuthenticator newUserAuthenticator;
    private MongoEmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        ldapTemplate = mock(LdapTemplate.class);
        employeeRepository = Mockito.mock(MongoEmployeeRepository.class);
        newUserAuthenticator = new NewUserAuthenticator(ldapTemplate, employeeRepository);
    }

    @Test
    public void validatesUserWhoExistsInCauthAndNotAlreadyInSystem() throws Exception {
        String username = "someUsername";
        when(employeeRepository.findByName(username)).thenReturn(Optional.empty());
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
        when(employeeRepository.findByName(username)).thenReturn(Optional.of(new EmployeeDetails(username, Level.JuniorDeveloper, Role.Developer, new Team("A"), false)));
        when(ldapTemplate.list(String.format(DN_SEARCH_BASE, username))).thenReturn(emptyList());
        newUserAuthenticator.validateUserCanBeCreated(username);
    }

}