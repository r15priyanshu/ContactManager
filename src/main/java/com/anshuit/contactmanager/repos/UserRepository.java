package com.anshuit.contactmanager.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anshuit.contactmanager.entities.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {
	
	public User findUserByEmail(String email);
	public User findUserByUsername(String username);
	public User findUserByEmailAndPassword(String email,String password);
	
	@Modifying
	@Query("update User u set u.image =:filename where u.uid =:userid")
	public int updateProfilePic(@Param("userid") int userid,@Param("filename") String filename);
}
