package com.s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.s.Entity.User;
import com.s.helper.Message;
import com.s.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userepo;

	@GetMapping("/") 
	public String home() {
		return "home";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model m) {
		m.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/do_register")
	public String register(@Valid @ModelAttribute User user, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model m, HttpSession s) {

		try {
			 if (!agreement) {
				System.out.println("you have not agreed terms and conditions");
				throw new Exception("you have not agreed terms and conditions");
			}

			if (result.hasErrors()) {
				System.out.println("ERROR" + result.toString());
				m.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");  
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("agreement" + agreement);
			System.out.println(user);

			User u = this.userepo.save(user);
			m.addAttribute("user", new User());
			s.setAttribute("msg", new Message("Successfully Registered!!", "alert-success"));
			//s.removeAttribute("msg");

			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("user", user);
			s.setAttribute("msg", new Message("ERROR!" + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	// handler for custom login-->

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

}
