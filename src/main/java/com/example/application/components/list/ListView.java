package com.example.application.components.list;

import com.example.application.backend.entity.Company;
import com.example.application.backend.entity.Contact;
import com.example.application.backend.service.CompanyService;
import com.example.application.backend.service.ContactService;
import com.example.application.components.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout {
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

    private final ContactForm form;
    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();

    private final ContactService contactService;

    public ListView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        form = new ContactForm(companyService.getAllCompany());
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e->closeEditor());

        final Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private  void deleteContact(ContactForm.DeleteEvent evt) {
        contactService.deleteContact(evt.getContact());
        updateList();
        closeEditor();
    }

    private  void saveContact(ContactForm.SaveEvent evt) {
        contactService.save(evt.getContact());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editor");
    }


    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateList());

        final Button addContactButton = new Button("Add contact", click -> addContact());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void updateList() {
        grid.setItems(contactService.getAllContact(filterText.getValue()));
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

        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

    }

    private void editContact(Contact value) {
        if (value == null){
            closeEditor();
        }
        form.setContact(value);
        form.setVisible(true);

        addClassName("editing");
    }
}
