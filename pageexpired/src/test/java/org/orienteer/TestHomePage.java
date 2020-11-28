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
		tester.getSession().invalidate();
		tester.startPage(HomePage.class);
		tester.assertRenderedPage(HomePage.class);
	}
	
	@Test
	public void testStartPageByInstance()
	{
		tester.getSession().invalidate();
		tester.startPage(new HomePage());

		tester.assertRenderedPage(HomePage.class);
	}
	
	@Test
	public void testStartPageByInstanceOfStartComponentInPage()
	{
		tester.getSession().invalidate();
		BaseWicketTester.StartComponentInPage page = new BaseWicketTester.StartComponentInPage();
		page.setMarkup(Markup.of("<html></html>"));
		tester.startPage(page);

		tester.assertRenderedPage(BaseWicketTester.StartComponentInPage.class);
	}
	
	@Test
	public void testStartPageByInstanceOfStartComponentInPageButWithStaticMarkup()
	{
		tester.getSession().invalidate();
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
		tester.getSession().invalidate();
		HomePage page = new HomePage() {
			public org.apache.wicket.markup.IMarkupFragment getMarkup() {
				return Markup.of("<html></html>");
			};
		};
		tester.startPage(page);

		tester.assertRenderedPage(BaseWicketTester.StartComponentInPage.class);
	}
	
	
	@Test
	public void testStartMarkupPageByInstance()
	{
		tester.getSession().invalidate();
		tester.startPage(new MarkupPage());

		tester.assertRenderedPage(MarkupPage.class);
	}
	
	@Test
	public void testStartStatefulPageByInstance()
	{
		tester.getSession().invalidate();
		tester.startPage(new StatefulPage(Model.of("RANDOM")));

		tester.assertRenderedPage(StatefulPage.class);
	}
	
	@Test
	public void testStartSimplePageByInstance()
	{
		tester.getSession().invalidate();
		tester.startPage(new SimplePage());

		tester.assertRenderedPage(SimplePage.class);
	}
	
	@Test
	public void testStartSimplePageByClass()
	{
		tester.getSession().invalidate();
		tester.startPage(SimplePage.class);

		tester.assertRenderedPage(SimplePage.class);
	}
	
	@Test
	public void testStartComponentInPage() {
		tester.getSession().invalidate();
		System.out.println("RequestCycle ID:"+ RequestCycle.get().hashCode());
		HomePanel panel = new HomePanel("panel");
		tester.startComponentInPage(panel);
	}
}
