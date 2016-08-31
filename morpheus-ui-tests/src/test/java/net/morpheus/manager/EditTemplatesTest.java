package net.morpheus.manager;

import net.morpheus.MorpheusTestCase;
import org.junit.Test;

import static net.morpheus.matchers.ElementMatchers.isDisplayed;

public class EditTemplatesTest extends MorpheusTestCase {

    @Test
    public void canEditATemplate() throws Exception {
        given(theManager.isLoggedIn());

        when(theManager.navigatesToEditTemplate());
        when(theManager.entersANewSkill());
        when(theManager.savesTheTemplate());

        then(aNotice.ofSuccess(), isDisplayed());
    }

    @Test
    public void canDeleteASkillFromATemplate() throws Exception {
        given(theManager.isLoggedIn());

        when(theManager.navigatesToEditTemplate());
        when(theManager.clicksDeleteSkill());
        when(theManager.confirms());
        when(theManager.savesTheTemplate());

        then(aNotice.ofSuccess(), isDisplayed());
    }
}
