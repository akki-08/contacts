package com.contacts.Service;

import com.contacts.Entity.Contact;
import com.contacts.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(Contact contact){
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContact(){
        return contactRepository.findAll();
    }
    public Optional<Contact> getContactByFirstName(String firstName){
        return contactRepository.findByFirstName(firstName);
    }

    public Optional<Contact> getContactById(int id) {
        return contactRepository.findById(id);
    }

    public void deleteContactById(int id){
        if(contactRepository.findById(id).isPresent()){
            contactRepository.deleteById(id);
        }
    }
}
