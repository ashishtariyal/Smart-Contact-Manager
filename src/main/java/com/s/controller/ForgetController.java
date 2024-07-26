package com.s.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.s.Entity.User;
import com.s.repository.UserRepository;
import com.s.service.EmailService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class ForgetController {

	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailService service;
	Random random = new Random(1000);

	@RequestMapping("/forgot")
	public String forget() {

		return "forgot";

	}

	@PostMapping("/sendOTP")
	public String sendotp(@RequestParam("email") String email, HttpSession s) {

		System.out.println(email);
		// generating OTP of 4 digits-->

		int otp = random.nextInt(9999);
		System.out.println("otp" + otp);

		String subject = "OTP from Contact Manger";
		String message = "" + "<div style='border:1px solid #e2e2e2; padding:20px'>" + "<h1>" + "OTP is : " + "<b>"
				+ otp + "</b>" + "</h1>" + "</div>";
		String to = email;
		s.setAttribute("myotp", otp);
		s.setAttribute("email", email);
		//first here it will check that this type of email is valid in db or not .....
	String useremail=	(String) s.getAttribute("email");
	      User user = this.repo.getuserByUsername(useremail);
	      if(user==null) {
	    	  System.out.println("This email id is not registered!!!");
	    	  s.setAttribute("msg","This Email ID is not Registered");
	    	  return "forgot";
	      }
		
		boolean b = this.service.sendEmail(subject, message, to);
		if (b) {
			
			return "verify_OTP";

		} else {
			System.out.println("Check Your Email id!!!");
			return "forgot";
		}
	}

	@PostMapping("/verify_otp")
	public String verifyingOTP(@RequestParam("otp") int otp, HttpSession s) {
		int myotp = (int) s.getAttribute("myotp");
		String email = (String) s.getAttribute("email");

		if (myotp == otp) {
			// password change form-->

			User user = this.repo.getuserByUsername(email);
			if (user == null) {
				System.out.println("User does'nt Exist With this  Email id!!!");
				return "forgot";
			} else {
				// send change password form
				return "password_change_form";
			}

		} else {
			System.out.println("Wrong OTP !!!!");
			return "verify_OTP";
		}

	}

	@PostMapping("/change-password")
	public String changepassword(@RequestParam("newpassword") String newpassword, HttpSession s) {
		String email = (String) s.getAttribute("email");
		User user = this.repo.getuserByUsername(email);
		user.setPassword(this.encoder.encode(newpassword));
		this.repo.save(user);
		System.out.println("password changed");

		return "redirect:/signin?change=password change successfully!!";

	}
}
