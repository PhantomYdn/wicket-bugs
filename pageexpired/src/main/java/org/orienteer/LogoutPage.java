package org.orienteer;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class LogoutPage extends WebPage {

	public LogoutPage(IModel<String> userNameModel) {
		super(userNameModel);
		add(new Label("username", userNameModel));
	}

	@Override
	protected void onRender() {
		super.onRender();
		((AuthWebSession)getSession()).signOut();
	}
	
	
	
}