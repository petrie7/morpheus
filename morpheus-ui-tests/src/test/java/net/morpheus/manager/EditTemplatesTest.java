package net.morpheus.manager;

import net.morpheus.MorpheusTestCase;
import org.junit.Test;

import static net.morpheus.matchers.ElementMatchers.isDisplayed;

public class EditTemplatesTest extends MorpheusTestCase {

    @Test
    public void canEditATemplate() throws Exception {
        given(aManager.isLoggedIn());

        when(theUser.navigatesToEditTemplate());
        when(theUser.entersANewSkill());
        when(theUser.savesTheTemplate());

        then(aNotice.ofSuccess(), isDisplayed());
    }

    @Test
    public void canDeleteASkillFromATemplate() throws Exception {
        given(aManager.isLoggedIn());

        when(theUser.navigatesToEditTemplate());
        when(theUser.clicksDeleteSkill());
        when(theUser.confirms());
        when(theUser.savesTheTemplate());

        then(aNotice.ofSuccess(), isDisplayed());
    }
}
