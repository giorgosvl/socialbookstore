package myy803.socialbookstore.controllers;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import myy803.socialbookstore.services.BookRequestsService;
import myy803.socialbookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BookRequestsController {

    @Autowired
    BookRequestsService bookRequestsService;

    @RequestMapping("/user/request_book")
    public String request(@RequestParam("selected_book_id") int bookId, Model model) { //SMELL **not MODEL used**
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        bookRequestsService.addRequestingUser(bookId,username);
        return "redirect:/user/dashboard";
    }

    @RequestMapping("/user/requests")
    public String showUserBookRequests(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("requests", bookRequestsService.retrieveRequests(username));
        return "/user/book_requests";
    }

    @RequestMapping("/user/book_requesting_users")
    public String showRequestingUsersForBookOffer(@RequestParam("selected_offer_id") int bookId, Model model) {

        model.addAttribute("requesting_users", bookRequestsService.retrieveRequestingUserProfiles(bookId));
        model.addAttribute("book_id", bookId);

        return "/user/requesting_users";
    }

    @RequestMapping("/user/accept_request")
    public String acceptRequest(@RequestParam("selected_user") String selected_user, @RequestParam("book_id") int bookId, Model model) {
        /*
         * TODO - have to implement this user story
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String owner = authentication.getName();
        bookRequestsService.acceptBookRequest(bookId,selected_user,owner);
        System.err.println("Selected user: " + selected_user + " for book id: " + bookId);

        return "redirect:/user/dashboard";
    }

    @RequestMapping("/user/delete_book_request")
    public String deleteBookRequest(@RequestParam("selected_request_id") int bookId, Model model) {
        /*
         * TODO - have to implement this user story
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        bookRequestsService.deleteBookRequest(bookId,username);

        return "redirect:/user/dashboard";
    }
}
