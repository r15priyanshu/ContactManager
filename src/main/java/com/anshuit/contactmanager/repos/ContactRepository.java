package com.anshuit.contactmanager.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anshuit.contactmanager.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("FROM Contact AS c WHERE c.user.userId =:userId")
	public Page<Contact> findContactsByUser(@Param("userId") int userid, Pageable pageable);

	@Query("FROM Contact AS c WHERE c.user.userId  =:userId AND c.fullname LIKE %:fullname%")
	public List<Contact> findContactsByUserSearchParameter(@Param("userId") int userid,
			@Param("fullname") String fullname);
}
