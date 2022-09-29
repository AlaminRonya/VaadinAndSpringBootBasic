package com.example.application.components;

import com.example.application.backend.entity.Company;
import com.example.application.backend.entity.Contact;
import com.example.application.backend.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("")
public class MainView extends VerticalLayout {
//    private Button button = new Button("Click Me");
//    private DatePicker datePicker = new DatePicker("Pick the Date");
//    public MainView() {
//        HorizontalLayout layout = new HorizontalLayout(button, datePicker);
//        layout.setDefaultVerticalComponentAlignment(Alignment.END);
//        add(layout);
//        button.addClickListener(click-> add(
//                new Paragraph("Clicked: "+datePicker.getValue())
//        ));
//    }

    // TODO: Second Coding View Front Page

    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField textField = new TextField();

    private final ContactService contactService;

    public MainView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();
        add(textField, grid);
        updateList();
    }

    private void configureFilter() {
        textField.setPlaceholder("Filter by name...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e->updateList());
    }

    private void updateList() {
        grid.setItems(contactService.getAllContact(textField.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("firstName","lastName","email","status");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-":company.getName();
        }).setHeader("Company");

    }
}
