package com.contacts.Repository;

import com.contacts.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    Optional<Contact> findByFirstName(String firstName);
}
