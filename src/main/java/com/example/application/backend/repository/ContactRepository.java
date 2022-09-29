package com.example.application.backend.repository;

import com.example.application.backend.entity.Contact;
import com.vaadin.flow.router.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("select c from Contact c "+
    "where lower(c.firstName) like lower(concat('%',:value, '%'))" +
            "or lower(c.lastName) like lower(concat('%',:value, '%') ) ")
    List<Contact> search(@Param("value") String value);
}
