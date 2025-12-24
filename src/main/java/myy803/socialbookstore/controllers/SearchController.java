package myy803.socialbookstore.controllers;

import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/user/search")
    public String showSearchForm(Model model) {
        SearchDto searchDto = new SearchDto();
        model.addAttribute("searchDto", searchDto);

        return "user/search_form";
    }
    @RequestMapping("/user/search_offer")
    public String search(@ModelAttribute("searchDto") SearchDto searchDto, Model model) {

        model.addAttribute("books",  searchService.retrieveSearches(searchDto));
        return "user/search_results";
    }

}
