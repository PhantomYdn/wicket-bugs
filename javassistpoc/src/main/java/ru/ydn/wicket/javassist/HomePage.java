package ru.ydn.wicket.javassist;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		// TODO Add your page's components here

    }
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
	}
	
	@Override
	public void renderPage() {
		super.renderPage();
	}
	
}
