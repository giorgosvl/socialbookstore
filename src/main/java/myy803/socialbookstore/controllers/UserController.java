package myy803.socialbookstore.controllers;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import myy803.socialbookstore.services.NotificationService;
import myy803.socialbookstore.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import myy803.socialbookstore.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

	@Autowired
    UserService userService; //NOT USED???

	@Autowired
	private UserServiceImpl userServiceimpl;

	@Autowired
	private NotificationService notificationService;


		
    @RequestMapping("/user/dashboard")
    public String getUserMainMenu(){
    	return "user/dashboard";
    }
    
    @RequestMapping("/user/profile")
    public String retrieveProfile(Model model){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    	String username = authentication.getName();
    	System.err.println("Logged use: " + username);

		model.addAttribute("categories", userServiceimpl.retrieveCategories());
    	model.addAttribute("profile", userServiceimpl.retrieveUserProfile(username));
    	
    	return "user/profile";
    }
    
    @RequestMapping("/user/save_profile")
    public String saveProfile(@ModelAttribute("profile") UserProfileDto userProfileDto, Model theModel) { // SMELL **Model not used**
    	System.err.println(userProfileDto.toString());

		userServiceimpl.saveUserProfile(userProfileDto);

    	return "user/dashboard";
    }
	@RequestMapping("/user/notifications")
	public String showNotifications(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		model.addAttribute("notifications", notificationService.retrieveUserNotifications(username));

		return"user/notifications";
	}

	@RequestMapping("/user/delete_notification")
	public String deleteBookRequest(@RequestParam("selected_notification_id") int notificationId, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		notificationService.removeNotification(notificationId,username);
		return"redirect:/user/notifications";

	}

}
