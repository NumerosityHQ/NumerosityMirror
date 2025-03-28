package org.vaadin.numerosity.Featureset.Supporter;

import com.vaadin.flow.component.button.Button;

public class OptionButton extends Button {

    private String data;

    public OptionButton() {
        super();
    }

    public OptionButton(String text) {
        super(text);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
