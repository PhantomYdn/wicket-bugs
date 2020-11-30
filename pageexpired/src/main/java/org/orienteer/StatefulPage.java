package org.orienteer;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class StatefulPage extends WebPage {

	public StatefulPage(IModel<?> model) {
		super(model);
		add(new Label("label", model));
		setStatelessHint(false);
	}
	
	
}
