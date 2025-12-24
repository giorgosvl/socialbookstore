package myy803.socialbookstore.controllers;

import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import myy803.socialbookstore.services.BookOffersService;
import myy803.socialbookstore.services.BookRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BookOffersController {
    @Autowired
    private BookOffersService bookOffersService;


    @RequestMapping("/user/offers")
    public String listBookOffers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("offers", bookOffersService.retrieveBookOffers(username));
        return "user/offers";
    }

    @RequestMapping("/user/show_offer_form")
    public String showOfferForm(Model model) {
        model.addAttribute("categories", bookOffersService.retrieveBookCategories());
        model.addAttribute("offer", new BookDto());

        return "user/offer-form";
    }

    @RequestMapping("/user/save_offer")
    public String saveOffer(@ModelAttribute("offer") BookDto bookOfferDto, Model model) { // SMELL??
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        bookOffersService.saveBookOffer(bookOfferDto,username);

        return "redirect:/user/offers";
    }

    @RequestMapping("/user/delete_book_offer")
    public String deleteBookOffer(@RequestParam("selected_offer_id") int bookId, Model model) {
        /*
         * TODO - have to implement this user story
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        bookOffersService.deleteBookOffer(bookId,username);

        return "redirect:/user/dashboard";
    }

}
