package net.morpheus.developer;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeRecord;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;

public class DeveloperCommentsTest extends MorpheusTestCase {

    @Ignore
    @Test
    public void aDeveloperCanEditTheirComments() throws Exception {
        given(aDeveloperExists());
        and(hasSomeSkillsWithComments());
        and(theDeveloper.isLoggedIn());

        when(theDeveloper.entersAComment());
        when(theDeveloper.savesTheTemplate());

        then(theDeveloper(), hasTheComment());
    }

    private GivensBuilder hasSomeSkillsWithComments() {
        return interestingGivens -> {
            employeeRecordRepository.create(employeeRecordForTest);
            return interestingGivens;
        };
    }

    private StateExtractor<EmployeeRecord> theDeveloper() {
        return null;
    }

    private Matcher<EmployeeRecord> hasTheComment() {
        return null;
    }
}
