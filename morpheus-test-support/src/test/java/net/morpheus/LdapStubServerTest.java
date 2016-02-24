package net.morpheus;

import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFException;
import net.morpheus.domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQueryBuilder;

import java.util.List;

public class LdapStubServerTest {

    private LdapStubServer ldapStubServer;

    @Before
    public void startLdapServer() throws LDAPException {
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
        contextSource.setUrl("ldap://localhost:51616");
        contextSource.setBase("dc=is,dc=company,dc=com");
        LdapTemplate template = new LdapTemplate(contextSource);

        List<String> uid = template.find(LdapQueryBuilder.query().where("uid").is("Employee 1"), String.class);
        System.out.println(uid);

    }

}