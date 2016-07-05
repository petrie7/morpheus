package net.morpheus.service;

import net.morpheus.exception.UserNotInCauthException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.LdapTemplate;

import static java.util.Collections.emptyList;
import static net.morpheus.service.NewUserAuthenticator.DN_SEARCH_BASE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewUserAuthenticatorTest {

    private LdapTemplate ldapTemplate;
    private NewUserAuthenticator newUserAuthenticator;

    @Before
    public void setUp() {
        ldapTemplate = mock(LdapTemplate.class);
        newUserAuthenticator = new NewUserAuthenticator(ldapTemplate);
    }

    @Test
    public void validatesUserExistsInCauth() throws Exception {
        String username = "someUsername";
        when(ldapTemplate.list(String.format(DN_SEARCH_BASE, username))).thenReturn(emptyList());
        newUserAuthenticator.validateUserExistsInCauth(username);
    }

    @Test(expected = UserNotInCauthException.class)
    public void throwsExceptionWhenUserDoesNotExist() {
        String username = "someNonExistentUser";
        when(ldapTemplate.list(String.format(DN_SEARCH_BASE, username))).thenThrow(new RuntimeException());
        newUserAuthenticator.validateUserExistsInCauth(username);
    }

}