package com.contactmanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DynamicInsert
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String fullname;
	private String nickname;
	private String email;
	
	@Column(length = 2000)
	private String description;
	@Column(columnDefinition="varchar(50) default 'default.jpg'")
	private String image;
	private String mobile;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	@JsonIgnore
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(int cid, String fullname, String nickname, String email, String description, String image,
			String mobile, User user) {
		super();
		this.cid = cid;
		this.fullname = fullname;
		this.nickname = nickname;
		this.email = email;
		this.description = description;
		this.image = image;
		this.mobile = mobile;
		this.user = user;
	}
	
	

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", fullname=" + fullname + ", nickname=" + nickname + ", email=" + email
				+ ", description=" + description + ", image=" + image + ", mobile=" + mobile + "]";
	}
	
}
