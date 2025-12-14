package com.anshuit.contactmanager.entities;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	private String fullname;
	private String nickname;
	private String email;

	@Column(length = 2000)
	private String description;
	@Column(columnDefinition = "varchar(50) default 'default.jpg'")
	private String image;
	private String mobile;

	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private AppUser user;

	public Contact() {
		super();
	}
}
