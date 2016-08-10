package net.morpheus.service;

import net.morpheus.exception.UserNotInCauthException;
import net.morpheus.persistence.EmployeeRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ldap.core.LdapTemplate;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;
import static net.morpheus.service.NewUserAuthenticator.DN_SEARCH_BASE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewUserAuthenticatorTest {

    private LdapTemplate ldapTemplate;
    private NewUserAuthenticator newUserAuthenticator;
    private EmployeeRecordRepository employeeRecordRepository;

    @Before
    public void setUp() {
        ldapTemplate = mock(LdapTemplate.class);
        employeeRecordRepository = Mockito.mock(EmployeeRecordRepository.class);
        newUserAuthenticator = new NewUserAuthenticator(ldapTemplate, employeeRecordRepository);
    }

    @Test
    public void validatesUserWhoExistsInCauthAndNotAlreadyInSystem() throws Exception {
        String username = "someUsername";
        when(employeeRecordRepository.findByName(username)).thenReturn(emptyList());
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
        when(employeeRecordRepository.findByName(username)).thenReturn(asList(anEmployeeRecord().withUsername(username).withSkills(null).build()));
        when(ldapTemplate.list(String.format(DN_SEARCH_BASE, username))).thenReturn(emptyList());
        newUserAuthenticator.validateUserCanBeCreated(username);
    }

}