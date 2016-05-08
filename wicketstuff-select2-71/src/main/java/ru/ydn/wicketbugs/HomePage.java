package ru.ydn.wicketbugs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.select2.Response;
import org.wicketstuff.select2.Select2MultiChoice;
import org.wicketstuff.select2.StringTextChoiceProvider;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private List<String> allTags = new ArrayList<String>(Arrays.asList("Please", "remove", "all", "tags"));

	public HomePage(final PageParameters parameters) {
		super(parameters);

		Form<?> form = new Form<>("form");
		IModel<Collection<String>> model = Model.of((Collection<String>)new ArrayList<String>(allTags));
		final Label modelValue = new Label("modelValue", model);
		form.add(modelValue.setOutputMarkupId(true));
		
		StringTextChoiceProvider provider = new StringTextChoiceProvider() {
			
			@Override
			public void query(String term, int page, Response<String> response) {
				if(term!=null) {
					for (String string : allTags) {
						if(string.startsWith(term)) response.add(string);
					}
				}
			}
			
		};

		final Select2MultiChoice<String> choice = new Select2MultiChoice<String>("tags", model, provider) {
			public Collection<String> getCurrent() {
				return getCurrentValue();
			}
		};
		final Label currentValue = new Label("currentValue", new PropertyModel<Collection<String>>(choice, "current"));
		form.add(currentValue.setOutputMarkupId(true));
		
		choice.add(new AjaxFormSubmitBehavior("change") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(modelValue);
				target.add(currentValue);
				target.add(choice);
			}
			
			@Override
			public boolean getDefaultProcessing() {
				return false;
			}
		});
		form.add(choice.setOutputMarkupId(true));
		add(form);

    }
}
