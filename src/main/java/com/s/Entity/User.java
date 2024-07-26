package com.s.Entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message = "Name Should not be Blank!!")
	@Size(min = 2,max=20,message = "Minimum 2 and maximum 20 characters are allowed")
	private String name;
	@Column(unique = true)
	@Email(regexp = "^[a-zA-z0-9+_.-]+@gmail\\.com$", message = "After @ gmail.com is required!!")
	@NotBlank(message = "Email can't be Blank!!")
	private String email;
	@NotBlank(message = "Password can't be Blank")
	private String password;
	private String role; 
	private boolean enabled;
	@Column(length = 500)
	@NotBlank(message = "Please Write Something Here About yourself")
	@Size(min = 5,max=500, message = "Minimum 5 and Maximum 500 characters are allowed")
	private String about;

	@OneToMany(cascade = CascadeType.ALL ,mappedBy = "user")
	private List<Contact> contacts = new ArrayList<>();

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public User(int id,
			@NotBlank(message = "Name Should not be Blank!!") @Size(min = 2, max = 20, message = "Minimum 2 and maximum 20 characters are allowed") String name,
			@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email!") String email,
			String password, String role, boolean enabled, String about, List<Contact> contacts) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.about = about;
		this.contacts = contacts;
	}


	@Override
	public String toString() {
	    return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
	            + ", enabled=" + enabled + ", about=" + about + "]";
	}

	public User() {
		super();
		
	}

	

}
