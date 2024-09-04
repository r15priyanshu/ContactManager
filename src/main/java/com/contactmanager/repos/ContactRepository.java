package com.contactmanager.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contactmanager.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("from Contact as c where c.user.uid =:userid")
	public Page<Contact> findContactsByUser(@Param("userid") int userid,Pageable pageable);
	
	@Query("from Contact as c where c.user.uid  =:userid and c.fullname like %:fullname%")
	public List<Contact> findContactsByUserSearchParameter(@Param("userid") int userid,@Param("fullname") String fullname);
}
