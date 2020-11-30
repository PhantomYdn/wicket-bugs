package org.orienteer;

import org.apache.wicket.Session;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.tester.BaseWicketTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester = new WicketTester(new WicketApplication());

	@Test
	public void testStartPageByClass()
	{
		signIn();
		tester.startPage(HomePage.class);
		tester.assertRenderedPage(HomePage.class);
		signOut();
		tester.startPage(HomePage.class);
		tester.assertRenderedPage(HomePage.class);
	}
	
	@Test
	public void testStartPageByInstance()
	{
		signIn();
		tester.startPage(new HomePage());
		tester.assertRenderedPage(HomePage.class);
		signOut();
		tester.startPage(new HomePage());
		tester.assertRenderedPage(HomePage.class);
	}
	
	@Test
	public void testStartPageByInstanceOfStartComponentInPage()
	{
		signOut();
		BaseWicketTester.StartComponentInPage page = new BaseWicketTester.StartComponentInPage();
		page.setMarkup(Markup.of("<html></html>"));
		tester.startPage(page);

		tester.assertRenderedPage(BaseWicketTester.StartComponentInPage.class);
	}
	
	@Test
	public void testStartPageByInstanceOfStartComponentInPageButWithStaticMarkup()
	{
		signOut();
		BaseWicketTester.StartComponentInPage page = new BaseWicketTester.StartComponentInPage() {
			private static final long serialVersionUID = 1L;

			public org.apache.wicket.markup.IMarkupFragment getMarkup() {
				return Markup.of("<html></html>");
			};
		};
		tester.startPage(page);

		tester.assertRenderedPage(BaseWicketTester.StartComponentInPage.class);
	}
	
	@Test
	public void testStartPageByInstanceOfAPageButWithStaticMarkup()
	{
		signOut();
		HomePage page = new HomePage() {
			public org.apache.wicket.markup.IMarkupFragment getMarkup() {
				return Markup.of("<html><span wicket:id=\"version\"></span><a wicket:id=\"logout\">Logout</a></html>");
			};
		};
		tester.startPage(page);

		tester.assertRenderedPage(HomePage.class);
	}
	
	
	@Test
	public void testStartMarkupPageByInstance()
	{
		signOut();
		tester.startPage(new MarkupPage());

		tester.assertRenderedPage(MarkupPage.class);
	}
	
	@Test
	public void testStartStatefulPageByInstance()
	{
		signOut();
		tester.startPage(new StatefulPage(Model.of("RANDOM")));

		tester.assertRenderedPage(StatefulPage.class);
	}
	
	@Test
	public void testStartSimplePageByInstance()
	{
		signOut();
		tester.startPage(new SimplePage());

		tester.assertRenderedPage(SimplePage.class);
	}
	
	@Test
	public void testStartSimplePageByClass()
	{
		signOut();
		tester.startPage(SimplePage.class);

		tester.assertRenderedPage(SimplePage.class);
	}
	
	@Test
	public void testStartComponentInPage() {
		signOut();
		HomePanel panel = new HomePanel("panel");
		tester.startComponentInPage(panel);
	}
	
	@Test
	public void testLogoutPage() {
		String username = "TESTUSERNAME";
		LogoutPage page = new LogoutPage(Model.of(username));
		tester.startPage(page);
		tester.assertRenderedPage(LogoutPage.class);
		tester.assertContains(username);
	}
	
	@Test
	public void testLogout2Page() {
		tester.startPage(new HomePage());
		tester.assertRenderedPage(HomePage.class);
		tester.clickLink("logout");
		tester.assertRenderedPage(LogoutPage.class);
	}
	
	private void signIn() {
		((AuthWebSession)tester.getSession()).signIn("admin", "admin");
	}
	
	private void signOut() {
		((AuthWebSession)tester.getSession()).signOut();
	}
}
