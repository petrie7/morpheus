package net.morpheus.stub;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldif.LDIFException;
import com.unboundid.ldif.LDIFReader;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Role;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.ArrayList;

import static com.unboundid.ldap.listener.InMemoryListenerConfig.createLDAPConfig;
import static java.lang.String.format;
import static net.morpheus.domain.Role.Developer;
import static net.morpheus.domain.Role.Manager;

public class LdapStubServer {

    private final InMemoryDirectoryServer directoryServer;
    private LDAPConnection connection;

    public static void main(String[] args) throws Exception {
        LdapStubServer ldapStubServer = new LdapStubServer();
        ldapStubServer.start();
        ldapStubServer.addEmployee(new Employee("Developer", Developer, new ArrayList<>()), "a");
        ldapStubServer.addEmployee(new Employee("Manager", Manager, new ArrayList<>()), "m");
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

    private String groupFor(Role role) {
        return format("cn=%s,ou=groups,ou=morpheus,ou=Applications,dc=isp,dc=company,dc=com", role.toString());
    }

    public LDAPConnection getConnection() {
        return connection;
    }
}