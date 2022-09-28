package com.example.application.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    private Button button = new Button("Click Me");
    private DatePicker datePicker = new DatePicker("Pick the Date");
    public MainView() {
        HorizontalLayout layout = new HorizontalLayout(button, datePicker);
        layout.setDefaultVerticalComponentAlignment(Alignment.END);
        add(layout);
        button.addClickListener(click-> add(
                new Paragraph("Clicked: "+datePicker.getValue())
        ));
    }
}
