package org.orienteer;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssPackageResource;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;

public class NotFoundPage extends WebPage {
	private static final long serialVersionUID = 1L;
	private CssResourceReference STYLE_CSS = new CssResourceReference(NotFoundPage.class, "style.css");

	public NotFoundPage(final PageParameters parameters) {
		super(parameters);

    }
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(STYLE_CSS));
	}
	
	@Override
	public boolean isVersioned() {
		return false;
	}
	
	@Override
	public boolean isErrorPage() {
		return true;
	}
}
