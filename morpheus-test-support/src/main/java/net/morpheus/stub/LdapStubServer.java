package net.morpheus.stub;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldif.LDIFException;
import com.unboundid.ldif.LDIFReader;
import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.Role;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

import static com.unboundid.ldap.listener.InMemoryListenerConfig.createLDAPConfig;
import static java.lang.String.format;
import static net.morpheus.domain.Level.Manager;
import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;

public class LdapStubServer {

    private final InMemoryDirectoryServer directoryServer;
    private LDAPConnection connection;

    public static void main(String[] args) throws Exception {
        LdapStubServer ldapStubServer = new LdapStubServer();
        ldapStubServer.start();
        ldapStubServer.addEmployee(anEmployeeRecord()
                        .withUsername("Laurence_Fishburne")
                        .build(),
                "a");

        ldapStubServer.addEmployee(anEmployeeRecord()
                        .withUsername("Manager")
                        .withLevel(Manager)
                        .withRole(Role.Manager)
                        .build(),
                "m");
    }

    public LdapStubServer() throws Exception {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=isp,dc=company,dc=com");
        InMemoryListenerConfig listenerConfig = createLDAPConfig("insecure", 51616);
        config.setListenerConfigs(listenerConfig);
        directoryServer = new InMemoryDirectoryServer(config);
        try (final InputStream in = new ClassPathResource("/ldap.ldif").getInputStream()) {
            directoryServer.importFromLDIF(true, new LDIFReader(in));
        }
    }

    public void start() throws LDAPException {
        directoryServer.startListening();
        connection = directoryServer.getConnection();
    }

    public void stop() {
        connection.close();
        directoryServer.shutDown(true);
    }

    public void addEmployee(EmployeeRecord employeeRecord, String password) throws LDIFException, LDAPException {
        connection.add(new AddRequest(
                "dn:" + dnFor(employeeRecord.username()),
                "objectClass: top",
                "objectClass: inetOrgPerson",
                "objectClass: organizationalPerson",
                "objectClass: person",
                "cn: " + employeeRecord.username(),
                "sn: " + employeeRecord.username(),
                "uid: " + employeeRecord.username(),
                "userPassword: " + password
        ));

        connection.modify(new ModifyRequest(
                groupFor(employeeRecord.role()),
                new Modification(ModificationType.ADD, "member", dnFor(employeeRecord.username()))
        ));
    }

    private String dnFor(String username) {
        return format("uid=%s,ou=People,dc=isp,dc=company,dc=com", username);
    }

    private String groupFor(Role role) {
        return format("cn=%s,ou=groups,ou=morpheus,ou=Applications,dc=isp,dc=company,dc=com", role.toString());
    }

    public LDAPConnection getConnection() {
        return connection;
    }
}