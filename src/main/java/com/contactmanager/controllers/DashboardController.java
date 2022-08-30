package com.contactmanager.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
import com.contactmanager.helper.FileUploader;
import com.contactmanager.helper.Message;
import com.contactmanager.helper.ValidUserCheck;
import com.contactmanager.repos.ContactRepository;
import com.contactmanager.services.DashboardService;

@Controller
@RequestMapping("/user")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@RequestMapping("/{username}/profile")
	public String profile(Model m, @PathVariable("username") String username, HttpSession session) {
		if (ValidUserCheck.check(session, username)) {
			return "profile";
		} else {
			Message message = new Message("You Need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/{username}/editprofile", method = RequestMethod.POST)
	public String editProfile(@ModelAttribute User user, Model m, RedirectAttributes redirectAttributes,
			HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		user.setUid(loggedInUser.getUid());
		user.setRegdate(loggedInUser.getRegdate());
		user.setImage(loggedInUser.getImage());
		user.setRole(loggedInUser.getRole());

		if (!user.getEmail().equals(loggedInUser.getEmail())) {
			if (dashboardService.checkUserByEmail(user.getEmail())) {
				Message message = new Message("Email Already In Use !!", "alert-danger");
				session.setAttribute("message", message);
				return "redirect:/user/" + loggedInUser.getUsername() + "/profile";
			}
		}
		
		if (!user.getUsername().equals(loggedInUser.getUsername())) {
			if (dashboardService.checkUserByUsername(user.getUsername())) {
				Message message = new Message("Username Already Taken !!", "alert-danger");
				session.setAttribute("message", message);
				return "redirect:/user/" + loggedInUser.getUsername() + "/profile";
			}
		}

		User updatedUser = dashboardService.editUser(user);
		if (updatedUser != null) {
			session.removeAttribute("loggedInUser");
			session.setAttribute("loggedInUser", updatedUser);
			Message message = new Message("Profile Edited Successfully !!", "alert-success");
			session.setAttribute("message", message);
		} else {
			Message message = new Message("Something Went Wrong !!", "alert-danger");
			session.setAttribute("message", message);
		}

		return "redirect:/user/" + loggedInUser.getUsername() + "/profile";
	}

	@RequestMapping(value = "/{username}/editprofilepic", method = RequestMethod.POST)
	public String editProfilePic(Model m, RedirectAttributes redirectAttributes, HttpSession session,
			@PathVariable("username") String username, @RequestParam("formimage") MultipartFile file) {
		User user = (User) session.getAttribute("loggedInUser");
		if (ValidUserCheck.check(session, username)) {
			String originalfilename = file.getOriginalFilename();
			if (file.isEmpty()) {
				Message message = new Message("No File Selected !!", "alert-danger");
				session.setAttribute("message", message);
				return "redirect:/user/" + user.getUsername() + "/profile";
			} else if (originalfilename.endsWith(".jpg") || originalfilename.endsWith(".png")
					|| originalfilename.endsWith(".jpeg")) {

				Date date = new Date();
				String finalfilename = date.getTime() + "-" + file.getOriginalFilename();
				try {
					File file1 = new ClassPathResource("static/uploads").getFile();
					String finalfilepath = file1.getAbsolutePath() + File.separator + finalfilename;
					if (FileUploader.uploadFile(file, finalfilepath)) {
						dashboardService.updateProfilePic(user.getUid(), finalfilename);
						session.removeAttribute("loggedInUser");
						user.setImage(finalfilename);
						session.setAttribute("loggedInUser", user);
						System.out.println("image uploaded successfully :" + finalfilename);
						Message message = new Message("Profile Pic Updated Successfully !!", "alert-success");
						session.setAttribute("message", message);
					} else {
						System.out.println("image upload failed :" + finalfilename);
						Message message = new Message("Profile Pic Upload Failed !!", "alert-danger");
						session.setAttribute("message", message);
						return "redirect:/user/" + user.getUsername() + "/profile";
					}
				} catch (IOException e) {
					Message message = new Message("Something Went Wrong !!", "alert-danger");
					session.setAttribute("message", message);
					return "redirect:/user/" + user.getUsername() + "/profile";
				}
				return "redirect:/user/" + user.getUsername() + "/profile";
			} else {
				Message message = new Message("Supported Extension are : .jpg , .jpeg and .png  !!", "alert-danger");
				session.setAttribute("message", message);
				return "redirect:/user/" + user.getUsername() + "/profile";
			}
		} else {
			Message message = new Message("You Need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:/user/" + user.getUsername() + "/profile";
		}
	}

	// currentpage -> represents the pagenumber in pagination concept [ or ,
	// requested page from the controller [ultimately it will be decreased by -1
	// because Page starts with 0 internally]]
	// contactsperpage -> represents the number of contacts to be served per page
	@RequestMapping("/{username}/contacts/{currentpage}")
	public String contacts(Model model, @PathVariable("username") String username,
			@PathVariable("currentpage") Integer currentpage, HttpSession session) {
		if (ValidUserCheck.check(session, username)) {
			User user = (User) session.getAttribute("loggedInUser");
			// System.out.println(user);
			int contactsperpage = 4;
			Pageable pageable = PageRequest.of(currentpage - 1, contactsperpage);
			Page<Contact> allcontacts = dashboardService.findContactsByUser(user, pageable);
			model.addAttribute("allcontacts", allcontacts);
			model.addAttribute("currentpage", currentpage);
			model.addAttribute("contactsperpage", contactsperpage);
			model.addAttribute("totalpages", allcontacts.getTotalPages());

			return "contacts";
		} else {
			Message message = new Message("You Need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:/login";
		}
	}

	// Post Method for adding new contact
	@RequestMapping(value = "/{username}/addcontacts", method = RequestMethod.POST)
	public String addNewContact(Model model, @ModelAttribute Contact contact,
			@RequestParam("formimage") MultipartFile file, @PathVariable("username") String username,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (ValidUserCheck.check(session, username)) {
			User user = (User) session.getAttribute("loggedInUser");
			contact.setUser(user);
			String originalfilename = file.getOriginalFilename();
			if (file.isEmpty()) {
				dashboardService.addContact(contact);
				Message message = new Message("Contact Added Successfully !!", "alert-success");
				session.setAttribute("message", message);
				return "redirect:/user/" + user.getUsername() + "/addcontacts";
			} else if (originalfilename.endsWith(".jpg") || originalfilename.endsWith(".png")
					|| originalfilename.endsWith(".jpeg")) {

				Date date = new Date();
				String finalfilename = date.getTime() + "-" + file.getOriginalFilename();
				contact.setImage(finalfilename);
				try {
					File file1 = new ClassPathResource("static/uploads").getFile();
					String finalfilepath = file1.getAbsolutePath() + File.separator + finalfilename;
					if (FileUploader.uploadFile(file, finalfilepath)) {
						System.out.println("image uploaded successfully :" + finalfilename);
						dashboardService.addContact(contact);
						Message message = new Message("Contact Added Successfully !!", "alert-success");
						session.setAttribute("message", message);
					} else {
						System.out.println("image upload failed :" + finalfilename);
						Message message = new Message("Image Upload Failed !!", "alert-danger");
						session.setAttribute("message", message);
						redirectAttributes.addFlashAttribute("contact", contact);
						return "redirect:/user/" + user.getUsername() + "/addcontacts";
					}
				} catch (IOException e) {
					e.printStackTrace();
					Message message = new Message("Something Went Wrong !!", "alert-danger");
					session.setAttribute("message", message);
					redirectAttributes.addFlashAttribute("contact", contact);
					return "redirect:/user/" + user.getUsername() + "/addcontacts";
				}
				return "redirect:/user/" + user.getUsername() + "/addcontacts";
			} else {
				Message message = new Message("Supported Extension are : .jpg , .jpeg and .png  !!", "alert-danger");
				session.setAttribute("message", message);
				redirectAttributes.addFlashAttribute("contact", contact);
				return "redirect:/user/" + user.getUsername() + "/addcontacts";
			}
		} else {
			Message message = new Message("You Need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:/login";
		}
	}

	// Get Method for deleting a contact
	@RequestMapping("/{username}/deletecontacts/{cid}")
	public String deleteContacts(Model model, @PathVariable("username") String username,
			@PathVariable("cid") Integer cid, HttpSession session) {
		if (ValidUserCheck.check(session, username)) {
			User user = (User) session.getAttribute("loggedInUser");
			Contact contact = dashboardService.findContactById(cid);
			if (contact == null) {
				Message message = new Message("No Such Contact !!", "alert-danger");
				session.setAttribute("message", message);
			} else {
				if (user.getUid() == contact.getUser().getUid()) {
					dashboardService.deleteContactById(cid);
					Message message = new Message("Contact Deleted Successfully !!", "alert-success");
					session.setAttribute("message", message);
				} else {
					Message message = new Message("Not Authorized to Perform This Operation !!", "alert-danger");
					session.setAttribute("message", message);
				}
			}
			return "redirect:/user/" + user.getUsername() + "/contacts/1";
		} else {
			Message message = new Message("You Need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:/login";
		}
	}

	// Get Method for showing addcontacts form
	@RequestMapping("/{username}/addcontacts")
	public String addcontacts(Model model, @PathVariable("username") String username, HttpSession session) {
		if (ValidUserCheck.check(session, username)) {
			Contact contact = (Contact) model.asMap().get("contact");
			if (contact == null) {
				contact = new Contact();
			}
			model.addAttribute("contact", contact);
			return "addcontacts";
		} else {
			Message message = new Message("You Need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:/login";
		}
	}

	// Post Method for editing a contact
	@RequestMapping(value = "/{username}/editcontacts/{cid}", method = RequestMethod.POST)
	public String editContacts(Model model, @ModelAttribute Contact contact,
			@RequestParam("formimage") MultipartFile file, @PathVariable("username") String username,
			@PathVariable("cid") Integer cid, HttpSession session, RedirectAttributes redirectAttributes) {
		if (ValidUserCheck.check(session, username)) {
			// System.out.println(contact);
			User user = (User) session.getAttribute("loggedInUser");
			Contact foundcontact = dashboardService.findContactById(cid);
			String originalfilename = file.getOriginalFilename();
			if (foundcontact == null) {
				Message message = new Message("No Such Contact !!", "alert-danger");
				session.setAttribute("message", message);
			} else {
				if (user.getUid() == foundcontact.getUser().getUid()) {
					contact.setUser(user);
					if (file.isEmpty()) {
						contact.setImage(foundcontact.getImage());
						dashboardService.addContact(contact);
						Message message = new Message("Contact Edited Successfully !!", "alert-success");
						session.setAttribute("message", message);
					} else if (originalfilename.endsWith(".jpg") || originalfilename.endsWith(".png")
							|| originalfilename.endsWith(".jpeg")) {

						Date date = new Date();
						String finalfilename = date.getTime() + "-" + file.getOriginalFilename();
						contact.setImage(finalfilename);
						try {
							File file1 = new ClassPathResource("static/uploads").getFile();
							String finalfilepath = file1.getAbsolutePath() + File.separator + finalfilename;
							if (FileUploader.uploadFile(file, finalfilepath)) {
								System.out.println("image uploaded successfully :" + finalfilename);
								dashboardService.addContact(contact);
								Message message = new Message("Contact Added Successfully !!", "alert-success");
								session.setAttribute("message", message);
							} else {
								System.out.println("image upload failed :" + finalfilename);
								Message message = new Message("Image Upload Failed !!", "alert-danger");
								session.setAttribute("message", message);
							}
						} catch (IOException e) {
							Message message = new Message("Something Went Wrong !!", "alert-danger");
							session.setAttribute("message", message);
						}
					} else {
						Message message = new Message("Supported Extension are : .jpg , .jpeg and .png  !!",
								"alert-danger");
						session.setAttribute("message", message);
					}
				} else {
					Message message = new Message("Not Authorized to Perform This Operation !!", "alert-danger");
					session.setAttribute("message", message);
				}
			}
			return "redirect:/user/" + user.getUsername() + "/contacts/1";
		} else {
			Message message = new Message("You Need to Login First !!", "alert-danger");
			session.setAttribute("message", message);
			return "redirect:/login";
		}
	}
}
