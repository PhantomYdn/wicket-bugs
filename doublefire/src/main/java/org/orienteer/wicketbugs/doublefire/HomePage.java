package org.orienteer.wicketbugs.doublefire;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		Form<?> form = new Form<>("form");
		form.add(new TextField<String>("input").setRequired(true)
				.add(new AjaxEventBehavior("focusout") {
					
					private int called=0;
					
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						String msg = "Called "+(++called)+" times. Page id = "+getPageId();
						System.out.println(msg);
						target.appendJavaScript("console.log('"+msg+"')");
					}
					
				}));
		add(form);
	}
}
