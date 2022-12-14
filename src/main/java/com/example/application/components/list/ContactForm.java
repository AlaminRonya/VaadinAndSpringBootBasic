package com.example.application.components.list;

import com.example.application.backend.entity.Company;
import com.example.application.backend.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ContactForm extends FormLayout {
//    TextField firstName = new TextField("First Name");
//    TextField lastName = new TextField("Last Name");
//    EmailField emailField = new EmailField("Email");
//    ComboBox<Contact.Status> status = new ComboBox<>("Status");
//    ComboBox<Company> company = new ComboBox<>("Company");
//
//    Button save  = new Button("Save");
//    Button delete  = new Button("Delete");
//    Button close  = new Button("Clear");

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Contact.Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    public ContactForm(List<Company> companies) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        status.setItems(Contact.Status.values());
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        add(
                firstName,
                lastName,
                email,
                status,
                company,
                createButtonsLayout()
        );
    }

    public void setContact(Contact contact){
        binder.setBean(contact);
        binder.readBean(contact);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> new DeleteEvent(this, binder.getBean()));
        close.addClickListener(click -> new CloseEvent(this));


        binder.addStatusChangeListener(evt ->save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save,delete,close);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private Contact contact;

        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
//            System.out.println("=================="+contact.getId());
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
