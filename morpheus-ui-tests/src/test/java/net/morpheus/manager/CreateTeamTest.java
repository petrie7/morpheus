package net.morpheus.manager;

import net.morpheus.MorpheusTestCase;
import org.junit.Test;

import static net.morpheus.MorpheusDataFixtures.someTeam;
import static net.morpheus.matchers.ElementMatchers.isDisplayed;

public class CreateTeamTest extends MorpheusTestCase {

    @Test
    public void canCreateTeam() throws Exception {
        given(theManager.isLoggedIn());

        when(theManager.navigatesToCreateTeam());
        when(theManager.entersATeamNameOf(someTeam()));

        then(aNotice.ofSuccess(), isDisplayed());
    }

    @Test
    public void canNotCreateTeamThatAlreadyExists() throws Exception {
        given(theManager.isLoggedIn());
        and(aTeamExists());

        when(theManager.navigatesToCreateTeam());
        when(theManager.entersATeamNameOf(theTeam));

        then(aNotice.ofError(), isDisplayed());
    }

}
