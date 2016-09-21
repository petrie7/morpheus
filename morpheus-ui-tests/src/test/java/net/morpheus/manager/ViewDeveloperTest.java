package net.morpheus.manager;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeDetails;
import org.junit.Before;
import org.junit.Test;

import static net.morpheus.MorpheusDataFixtures.someUsername;
import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;
import static net.morpheus.matchers.ElementMatchers.isDisplayed;

public class ViewDeveloperTest extends MorpheusTestCase {

    private EmployeeDetails newEmployee;

    @Before
    public void setup() {
        newEmployee = anEmployee()
                .withUsername(someUsername())
                .withIsArchived(true)
                .build();
        anArchivedDeveloperExists();
    }

    @Test
    public void searchOnlyShowsNonArchivedDevelopers() throws Exception {
        and(theManager.isLoggedIn());

        when(theManager.searchesFor(newEmployee.username()));

        then(aNotice.ofError(), isDisplayed());
    }

    private GivensBuilder anArchivedDeveloperExists() {
        return givens -> {
            employeeRepository.create(newEmployee);
            return givens;
        };
    }
}
