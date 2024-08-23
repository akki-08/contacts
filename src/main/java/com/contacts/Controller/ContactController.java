package com.contacts.Controller;

import com.contacts.Entity.Address;
import com.contacts.Entity.Contact;
import com.contacts.Entity.Project;
import com.contacts.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @PostMapping("/addContact")
    public Contact addContact(@RequestBody Contact contact){
        return contactService.saveContact(contact);
    }
    @GetMapping("/getAllContact")
    public List<Contact> getAllContact(){
        return contactService.getAllContact();
    }

    @GetMapping("/getContactByFirstName/{firstName}")
    public Optional<Contact> getContactByFirstName(@PathVariable String firstName){
        return contactService.getContactByFirstName(firstName);
    }

    @PutMapping("/updateContact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable int id, @RequestBody Contact contactDetails) {
        Optional<Contact> existingContactOpt = contactService.getContactById(id);

        if (existingContactOpt.isPresent()) {
            Contact existingContact = existingContactOpt.get();

            existingContact.setFirstName(contactDetails.getFirstName());
            existingContact.setLastName(contactDetails.getLastName());
            existingContact.setWebsite(contactDetails.getWebsite());
            existingContact.setProfilePicture(contactDetails.getProfilePicture());
            existingContact.setBirthday(contactDetails.getBirthday());
            existingContact.setNotes(contactDetails.getNotes());
            existingContact.setEmails(contactDetails.getEmails());
            existingContact.setPhones(contactDetails.getPhones());

            existingContact.getAddresses().clear();
            for (Address address : contactDetails.getAddresses()) {
                address.setContact(existingContact);
                existingContact.getAddresses().add(address);
            }

            if (contactDetails.getProject() != null) {
                Project existingProject = existingContact.getProject();
                if (existingProject != null) {
                    existingProject.setCompany(contactDetails.getProject().getCompany());
                    existingProject.setJobTitle(contactDetails.getProject().getJobTitle());
                    existingProject.setProjectDesc(contactDetails.getProject().getProjectDesc());
                } else {
                    existingContact.setProject(contactDetails.getProject());
                }
            } else {
                existingContact.setProject(null);
            }

            Contact updatedContact = contactService.saveContact(existingContact);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteContact/{id}")
    public void deleteContactById(@PathVariable int id){
        contactService.deleteContactById(id);
    }

}
