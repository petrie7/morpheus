package net.morpheus.manager;

import net.morpheus.MorpheusTestCase;
import org.junit.Ignore;
import org.junit.Test;

public class DeleteDeveloperTest extends MorpheusTestCase {

    @Ignore
    @Test
    public void canDeleteDeveloper() throws Exception {
        given(anDeveloperExists());
        and(theManager.isLoggedIn());
        and(theManager.isViewing(theDeveloper.getEmployeeForTest().username()));

        when(theManager.clicksDeleteDeveloper());

//        then(theDeveloper(), isDeleted());
    }
}
