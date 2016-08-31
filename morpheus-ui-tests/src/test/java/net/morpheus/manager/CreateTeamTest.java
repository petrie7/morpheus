package net.morpheus.manager;

import net.morpheus.MorpheusTestCase;
import org.junit.Test;

import static net.morpheus.matchers.ElementMatchers.isDisplayed;

public class CreateTeamTest extends MorpheusTestCase {

    @Test
    public void canCreateTeam() throws Exception {
        given(theManager.isLoggedIn());

        when(theManager.navigatesToCreateTeam());
        when(theManager.entersATeamName());

        then(aNotice.ofSuccess(), isDisplayed());
    }
}
