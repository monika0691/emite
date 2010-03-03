package com.calclab.hablar.selenium.search;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.calclab.hablar.search.client.SearchMessages;
import com.calclab.hablar.selenium.HablarSeleniumTest;
import com.calclab.hablar.selenium.tools.I18nHelper;
import com.calclab.hablar.selenium.tools.SeleniumConstants;

public class SearchTest extends HablarSeleniumTest {
    private I18nHelper i18n;

    @BeforeClass
    public void beforeClass() {
	i18n = new I18nHelper(SearchMessages.class);
    }

    @Test
    public void shouldShowSearchResults() {
	loginAndSearchClick();
	search.getTerm().clear();
	search.getTerm().sendKeys("test1\n");
	sleep(5000);
	search.waitForResult(i18n.get("searchResultsFor", "test1", 1));
    }

    @Test
    public void testBasicSearchNoResult() {
	loginAndSearchClick();
	search.getTerm().clear();
	search.getTerm().sendKeys("IMPOSSIBLE XXXXZZZÑÑÑ\n");
	sleep(5000);
	// zero it's plural in English
	search.waitForResult(i18n.get("searchResultsFor", "IMPOSSIBLE XXXXZZZÑÑÑ", 0));
    }

    @Test
    private void loginAndSearchClick() {
	login.signInDefUser();
	login.assertIsConnectedAs(SeleniumConstants.USERNODE);
	roster.getHeader().click();
	search.getAction().click();
	// Only to test accordion
	roster.getHeader().click();
	search.getHeader().click();
    }

    @Test
    private void loginSearchTestUserAndChat() {
	loginAndSearchClick();
	search.getTerm().clear();
	search.getTerm().sendKeys("test1\n");
	sleep(7000);
	// The menu it's not visible then we need to show it
	search.getResultName(SeleniumConstants.USERJID).click();
	// Now we can click the menu
	search.getResultMenu(SeleniumConstants.USERJID).click();
	search.ChatMenuItem().click();
    }
}