package com.weaxme;

import de.agilecoders.wicket.webjars.WicketWebjars;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class WebJarsTestCaseApplication extends WebApplication
{

	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}


	@Override
	public void init()
	{
		super.init();
		WicketWebjars.install(this);
	}
}
