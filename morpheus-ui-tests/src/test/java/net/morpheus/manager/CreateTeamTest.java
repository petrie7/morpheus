package net.morpheus.manager;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.Team;
import org.junit.Before;
import org.junit.Test;

import static net.morpheus.MorpheusDataFixtures.someTeam;
import static net.morpheus.matchers.ElementMatchers.isDisplayed;

public class CreateTeamTest extends MorpheusTestCase {

    private Team theTeamName;

    @Before
    public void setUp() {
        theTeamName = someTeam();
    }

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
        when(theManager.entersATeamNameOf(theTeamName));

        then(aNotice.ofError(), isDisplayed());
    }

    private GivensBuilder aTeamExists() {
        return interestingGivens -> {
            teamRepository.create(theTeamName);
            return interestingGivens;
        };
    }
}
