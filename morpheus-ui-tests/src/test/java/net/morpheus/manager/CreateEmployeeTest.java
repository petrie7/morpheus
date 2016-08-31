package net.morpheus.manager;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Team;
import org.junit.Before;
import org.junit.Test;

import static net.morpheus.MorpheusDataFixtures.someTeam;
import static net.morpheus.MorpheusDataFixtures.someUsername;
import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;
import static net.morpheus.matchers.ElementMatchers.isDisplayed;

public class CreateEmployeeTest extends MorpheusTestCase {

    private EmployeeDetails newEmployee;
    private EmployeeDetails anotherEmployee;
    private Team team = someTeam();

    @Before
    public void setup() {
        teamRepository.create(team);
        newEmployee = anEmployee()
                .withUsername("Pedro")
                .build();
        anotherEmployee = anEmployee()
                .withUsername(someUsername())
                .build();
    }

    @Test
    public void canCreateEmployee() throws Exception {
        given(anEmployeeIsInCauth());
        and(aManager.isLoggedIn());

        when(theUser.navigatesToCreateEmployee());
        when(theUser.enters(newEmployee, on(team)));

        then(aNotice.ofSuccess(), isDisplayed());
    }

    @Test
    public void cannotCreateEmployeeThatDoesNotExistInCauth() throws Exception {
        given(aManager.isLoggedIn());

        when(theUser.navigatesToCreateEmployee());
        when(theUser.enters(anotherEmployee, on(team)));

        then(aNotice.ofError(), isDisplayed());
    }

    private Team on(Team team) {
        return team;
    }

    private GivensBuilder anEmployeeIsInCauth() {
        return givens -> {
            ldapStubServer.addEmployee(newEmployee, "password");
            return givens;
        };
    }
}
