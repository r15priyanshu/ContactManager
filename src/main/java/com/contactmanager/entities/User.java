package com.contactmanager.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@DynamicInsert
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	@Column(unique = true,nullable = false)
	private String username;
	private String fullname;
	@Column(unique = true,nullable = false)
	private String email;
	private String password;
	@Column(columnDefinition="varchar(20) default 'NORMAL'")
	private String role;
	@Column(length=1000)
	private String about;
	
	@Column(columnDefinition="varchar(100) default 'default.jpg'")
	private String image;
	
	@Column(columnDefinition="datetime default now()")
	private Date regdate;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Contact> contacts;

	public User() {
		super();
	}

	public User(int uid, String username, String fullname, String email, String password, String role, String about,
			String image, Date regdate, List<Contact> contacts) {
		super();
		this.uid = uid;
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.about = about;
		this.image = image;
		this.regdate = regdate;
		this.contacts = contacts;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", fullname=" + fullname + ", email=" + email
				+ ", password=" + password + ", role=" + role + ", about=" + about + ", image=" + image + ", regdate="
				+ regdate + ", contacts=" + contacts + "]";
	}
	
	
	
	
}
