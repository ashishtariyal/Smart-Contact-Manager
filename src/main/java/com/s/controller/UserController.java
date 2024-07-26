package com.s.controller;


import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.s.Entity.Contact;
import com.s.Entity.User;
import com.s.helper.Message;
import com.s.repository.ContactRepository;
import com.s.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.razorpay.*;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repo;
	@Autowired
	private ContactRepository crepo;
	@Autowired
	private BCryptPasswordEncoder passencoder;
	
	
	

	// home

	@RequestMapping("/index")
	public String dashboard(Model m, Principal principal) {

		return "normal/user_dashboard";
	}

	// method for adding common data which user is logedin

	@ModelAttribute
	public void addCommonData(Model m, Principal p) {
		String username = p.getName();
		System.out.println(username);
		// get the user using username->
		User u = this.repo.getuserByUsername(username);
		System.out.println(u);
		m.addAttribute("user", u);
	}

	// show add form

	@RequestMapping("/addform")
	public String addForm(Model m) {
		m.addAttribute("contact", new Contact());
		return "normal/add_contact";
	}

	// adding in contact form

	@PostMapping("/process-contact")
	public String addcontact(@Valid @ModelAttribute() Contact contact, BindingResult result, Principal p, HttpSession s,
			Model m) {

		try {

			if (result.hasErrors()) {
				System.out.println("Error in fields plz check!!");
				m.addAttribute("contact", contact);
				return "normal/add_contact";
			}

			String name = p.getName();
			User u = this.repo.getuserByUsername(name);
			contact.setUser(u);
			u.getContacts().add(contact);
			this.repo.save(u);
			System.out.println(contact);

			s.setAttribute("msg", new Message("your contact is added -Add more", "success"));
			// s.removeAttribute("msg");

		} catch (Exception e) {
			e.printStackTrace();

			s.setAttribute("msg", new Message("Something went wrong Try Again!!", "danger"));
			// s.removeAttribute("msg");

		}

		return "normal/add_contact";
	}

	@GetMapping("/show-contact")
	public String showContact(Principal p, Model m) {
		String username = p.getName();
		User user = this.repo.getuserByUsername(username);

		List<Contact> contacts = this.crepo.findContactsByUser(user.getId());

		m.addAttribute("contacts", contacts);

		return "normal/show_contact";
	}
	// showing particular contact detail--->

	@GetMapping("/contact/{cid}")
	public String showContactDeatil(@PathVariable("cid") int cid, Model m, Principal p) {

		String name = p.getName();
		User user = this.repo.getuserByUsername(name);

		Optional<Contact> c = this.crepo.findById(cid);
		Contact contact = c.get();
		if (user.getId() == contact.getUser().getId()) {
			m.addAttribute("contact", contact);
		}
		return "/normal/contact_Detail";
	}

	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") int cid) {
		Optional<Contact> findById = this.crepo.findById(cid);
		if (findById.isPresent()) {
			Contact contact = findById.get();
			contact.setUser(null);
			this.crepo.delete(contact);
		}
		return "redirect:/user/show-contact";
	}

	@RequestMapping("/update/{cid}")
	public String update(@PathVariable("cid") int cid, Model m) {
		Contact contact = this.crepo.findById(cid).get();
		m.addAttribute("contact", contact);

		return "/normal/update_contact";
	}

	@PostMapping("/process-update")
	public String updatingForm(@ModelAttribute Contact contact, Principal p) {
		System.out.println(contact);

		try {

			User user = this.repo.getuserByUsername(p.getName());
			contact.setUser(user);
			this.crepo.save(contact);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/user/contact/" + contact.getCid();
	}

	@GetMapping("/profile")
	public String YourProfile() {

		return "/normal/your_profile";
	}

	@GetMapping("/setting")
	public String setting() {
		return "normal/setting";
	}

	@PostMapping("/change-password")
	public String changepassword(@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword, Principal p) {

		String name = p.getName();
		User user = this.repo.getuserByUsername(name);

		if (this.passencoder.matches(oldpassword, user.getPassword())) {

			user.setPassword(this.passencoder.encode(newpassword));
			this.repo.save(user);
			System.out.println("password changed...");
		} else {
			System.out.println("old password is wrong....");
		}

		System.out.println(oldpassword + "   " + newpassword);

		return "redirect:/user/index";

	}

	
	
	//payment ---->
	
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String ,Object> data) throws Exception {
		System.out.println(data);
		int amt =Integer.parseInt(data.get("amount").toString()) ;
		
		
		RazorpayClient client = new RazorpayClient("rzp_test_nS83NLXh1OyKVS","rp1IBd2Q0ibjJ7G8geIctfPZ");
		
		JSONObject ob = new JSONObject();
		ob.put("amount",amt*100);
		ob.put("currency","INR");
		ob.put("receipt","txn_235425");
		Order order = client.orders.create(ob);
		System.out.println(order);
		
		
		return order.toString();
	}
	
	
	
	
	
	
	
}
