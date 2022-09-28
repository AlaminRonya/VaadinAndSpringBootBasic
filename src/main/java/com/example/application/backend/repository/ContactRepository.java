package com.example.application.backend.repository;

import com.example.application.backend.entity.Contact;
import com.vaadin.flow.router.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}