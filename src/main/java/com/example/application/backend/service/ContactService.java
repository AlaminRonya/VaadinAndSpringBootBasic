package com.example.application.backend.service;

import com.example.application.backend.entity.Company;
import com.example.application.backend.entity.Contact;
import com.example.application.backend.repository.CompanyRepository;
import com.example.application.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactService {
    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public List<Contact> getAllContact(){
        return contactRepository.findAll();
    }
    public List<Contact> getAllContact(String value){
        if (value == null || value.isEmpty()){
            return contactRepository.findAll();
        }
        return contactRepository.search(value);
    }

    public Long getCount(){
        return contactRepository.count();
    }
    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }
    public void save(Contact contact){
        if (contact == null){
            LOGGER.log(Level.SEVERE, "Contact is null.Are you have connected your form to the applications?");
            return;
        }
        contactRepository.save(contact);
    }
    @PostConstruct
    public void populateTestData(){
        if (companyRepository.count() == 0){
            companyRepository.saveAll(
                    Stream.of("ABC", "DEF", "MNO", "XYZ")
                            .map(Company::new)
                            .collect(Collectors.toList()));

        }
        if (contactRepository.count() == 0){
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            contactRepository.saveAll(
                    Stream.of(
                            "Md Alamin",
                            "Md Sumon",
                            "Saima Akter",
                            "Pritha Saha",
                            "Jahangir Hoosain",
                            "Manik Islam"
                    ).map(name -> {
                        String[] split = name.split(" ");
                        Contact contact = new Contact();
                        contact.setFirstName(split[0]);
                        contact.setLastName(split[1]);
                        contact.setCompany(companies.get(r.nextInt(companies.size())));
                        contact.setStatus(Contact.Status.values()[r.nextInt(Contact.Status.values().length)]);
                        String email = (contact.getFirstName()+"."+contact.getLastName()+"@"+"gmail.com");
                        contact.setEmail(email);
                        return contact;

                    }).collect(Collectors.toList()));
        }


    }


}
