package org.orienteer;

import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class MarkupPage extends WebPage {
	
	public MarkupPage() {
		setStatelessHint(false);
		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
	}
	@Override
	public IMarkupFragment getMarkup() {
		return Markup.of("<html><span wicket:id=\"version\"></span></html>");
	}
}
