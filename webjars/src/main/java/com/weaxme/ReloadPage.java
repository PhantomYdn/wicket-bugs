package com.weaxme;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 * @author Vitaliy Gonchar
 */
public class ReloadPage extends WebPage {

    public ReloadPage() {
        add(new Label("reload", Model.of("Please wait. Wicket now is reload")));
    }

}
