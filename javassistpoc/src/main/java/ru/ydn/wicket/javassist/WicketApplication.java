package ru.ydn.wicket.javassist;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see ru.ydn.wicket.javassist.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	
	private static final Logger LOG = LoggerFactory.getLogger(WicketApplication.class);
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return WicketObjects.resolveClass("ru.ydn.wicket.javassist.HomePage");
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
		getApplicationSettings().setClassResolver(new JAClassResolver());

	}
}
