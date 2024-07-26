package com.s.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	@NotBlank
	@Size(min=2,max=25,message = "Minimum 2 and maximum 25 characters are allowed!!")
	private String cname;
	@NotBlank(message = "Please Enter Second Name")
	@Size(min=2,max=25,message = "Minimum 2 and maximum 25 characters are allowed!!")
	private String secondname;
	@NotBlank(message = "Please Enter Work field")
	private String work;
	@Email(regexp = "^[a-zA-z0-9+_.-]+@gmail\\.com$", message = "After @ gmail.com is required!!")
	@NotBlank(message = "Email can't be Blank!!")
	private String email;
	private String phone;
	@Column(length = 1000)
	@NotBlank(message = "Please Enter Description Name")
	@Size(min=2,max=250,message = "Minimum 2 and maximum 250 characters are allowed!!")
	private String description;
	
	@ManyToOne
	@JsonIgnore
	private User user;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Contact(int cid, String cname, String secondname, String work, String email, String phone,
			String description, User user) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.secondname = secondname;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.description = description;
		this.user = user;
	}

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", cname=" + cname + ", secondname=" + secondname + ", work=" + work + ", email="
				+ email + ", phone=" + phone + ", description=" + description + ", user=" + user + "]";
	} 

	

	

}
