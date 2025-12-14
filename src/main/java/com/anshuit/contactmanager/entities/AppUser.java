package com.anshuit.contactmanager.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "user")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(unique = true, nullable = false)
	private String username;
	private String fullname;
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	@Column(columnDefinition = "varchar(20) default 'NORMAL'")
	private String role;
	@Column(length = 1000)
	private String about;

	@Column(columnDefinition = "varchar(100) default 'default.jpg'")
	private String image;

	@Column(columnDefinition = "datetime default now()")
	private Date registrationDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Contact> contacts;

	public AppUser() {
		super();
	}
}
