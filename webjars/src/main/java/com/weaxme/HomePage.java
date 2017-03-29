package com.weaxme;

import com.weaxme.service.WebJarsFilter;
import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public static boolean wasReload = false;

	private final Label fontAwesomeLabel;

	private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);

	public HomePage() {
		add(new Label("wicketTitle", Model.of(getWicketVersionTitle())));
		add(new Label("webjarTitle", Model.of("Webjars 0.5.5")));
		add(new Label("weatherLabel", Model.of("Weather icons are in pom.xml. And it renders OK.")));
		fontAwesomeLabel = new Label("fontAwesomeLabel", Model.of("Before reload icons of font awesome is not visible"));
		add(fontAwesomeLabel);
		add(new Link<Void>("reload") {
			@Override
			public void onClick() {
				LOG.debug("reload");
				wasReload = true;
				HomePage.this.setResponsePage(new ReloadPage());
				WebJarsFilter.getInstance().reload();
			}
		});
    }


	@Override
	protected void onConfigure() {
		super.onConfigure();
		if (wasReload) {
			fontAwesomeLabel.setDefaultModelObject("Now icons of font awesome must be visible");
		}
	}

	private String getWicketVersionTitle() {
		return "Wicket " +  getApplication().getFrameworkSettings().getVersion();
    }


	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(
				new WebjarsCssResourceReference("weather-icons/current/css/weather-icons.css")));
		response.render(CssHeaderItem.forReference(
				new WebjarsCssResourceReference("font-awesome/4.7.0/css/font-awesome.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(HomePage.class, "style.css")));
	}
}
