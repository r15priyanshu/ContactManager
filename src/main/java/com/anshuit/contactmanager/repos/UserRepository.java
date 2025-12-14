package com.anshuit.contactmanager.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anshuit.contactmanager.entities.AppUser;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<AppUser, Integer> {

	public AppUser findUserByEmail(String email);

	public AppUser findUserByUsername(String username);

	public AppUser findUserByEmailAndPassword(String email, String password);

	@Modifying
	@Query("UPDATE AppUser u SET u.image =:filename WHERE u.userId =:userId")
	public int updateProfilePic(@Param("userId") int userid, @Param("filename") String filename);
}
