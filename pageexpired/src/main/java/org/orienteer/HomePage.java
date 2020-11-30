package org.orienteer;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage() {
		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
		add(new Link<Void>("logout") {
			 @Override
			 public void onClick() {
				 setResponsePage(new LogoutPage(Model.of("TESTUSERNAME")));
			 }
		});
		setStatelessHint(false);
	}
	
	
}
