package com.example.application.backend.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public enum Status{
        ImportedLead, NotContact, Contact, Customer, ClosedLost
    }
    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";
    @ManyToOne
    @JoinColumn
    private Company company;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Contact.Status status;

    @Email
    @NotNull
    @NotEmpty
    private String email = "";



    @Override
    public String toString() {
        return firstName+" "+lastName;
    }
}
