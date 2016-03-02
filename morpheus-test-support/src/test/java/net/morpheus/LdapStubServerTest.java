package net.morpheus;

import com.unboundid.ldap.sdk.*;
import com.unboundid.ldif.LDIFException;
import com.unboundid.util.LDAPSDKException;
import net.morpheus.domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.support.LdapContextSource;

import static com.unboundid.ldap.sdk.Filter.createEqualityFilter;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LdapStubServerTest {

    private final String hostname = "localhost";
    private final int port = 51616;
    private static final String PEOPLE_SEARCH_BASE_DN = "ou=People,dc=isp,dc=company,dc=com";
    private static final Filter PERSON_CLASS_FILTER = createEqualityFilter("objectClass", "person");
    private static final SearchRequest allUsersSearchRequest = new SearchRequest(PEOPLE_SEARCH_BASE_DN, SearchScope.SUB, PERSON_CLASS_FILTER, "uid", "cn", "dn", "userPassword");
    private LdapStubServer ldapStubServer;
    private LDAPConnection ldapConnection;
    private SearchResult allUsers;

    @Before
    public void startLdapServer() throws LDAPException {
        ldapConnection = new LDAPConnection();

        ldapStubServer = new LdapStubServer();
        ldapStubServer.start();
    }

    @After
    public void shutdownLdap() {
        ldapStubServer.stop();
    }

    @Test
    public void canAddAUserToLdap() throws LDIFException, LDAPException {
        ldapStubServer.addEmployee(new Employee("Employee 1"), "Password");

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://" + hostname + ":" + port);
        contextSource.setBase("dc=isp,dc=company,dc=com");

        allUsers = doWhileConnectedToLdapServer(ldap -> ldap.search(allUsersSearchRequest));

        assertThat(allUsers.getSearchEntries().size(), is(1));
    }

    private <T> T doWhileConnectedToLdapServer(final LdapAction<T> action) {
        final LDAPConnection connection = new LDAPConnection();

        try {
            ldapConnection.connect(hostname, port);
            ldapConnection.bind("ou=People,dc=isp,dc=company,dc=com", "");

            return action.execute(connection);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @FunctionalInterface
    private interface LdapAction<T> {
        T execute(LDAPInterface ldap) throws LDAPSDKException;
    }

}