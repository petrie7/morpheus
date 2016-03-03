package net.morpheus;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldif.LDIFException;
import com.unboundid.ldif.LDIFReader;
import net.morpheus.domain.Employee;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

import static com.unboundid.ldap.listener.InMemoryListenerConfig.createLDAPConfig;
import static java.lang.String.format;

public class LdapStubServer {

    private final InMemoryDirectoryServer directoryServer;
    private LDAPConnection connection;

    public LdapStubServer() throws LDAPException, IOException {
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

    public void addEmployee(Employee employee, String password) throws LDIFException, LDAPException {
        connection.add(new AddRequest(
                "dn:" + dnFor(employee.username()),
                "objectClass: top",
                "objectClass: inetOrgPerson",
                "objectClass: organizationalPerson",
                "objectClass: person",
                "cn: " + employee.username(),
                "sn: " + employee.username(),
                "uid: " + employee.username(),
                "userPassword: " + password
        ));

        connection.modify(new ModifyRequest(
                groupFor(employee.role()),
                new Modification(ModificationType.ADD, "member", dnFor(employee.username()))
        ));
    }

    private String dnFor(String username) {
        return format("uid=%s,ou=People,dc=isp,dc=company,dc=com", username);
    }

    private String groupFor(String role) {
        return format("cn=%s,ou=groups,ou=morpheus,ou=Applications,dc=isp,dc=company,dc=com", role);
    }

    public LDAPConnection getConnection() {
        return connection;
    }
}