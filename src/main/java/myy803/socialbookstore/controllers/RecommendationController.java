package myy803.socialbookstore.controllers;

import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsFactory;
import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsStrategy;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.RecommendationsDto;
import myy803.socialbookstore.services.RecommendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RecommendationController {

    @Autowired
    private RecommendationsService recommendationsService;

    @RequestMapping("/user/recom")
    public String showRecommendationsForm(Model model) {
        RecommendationsDto recomDto = new RecommendationsDto();
        model.addAttribute("recomDto", recomDto);

        return "user/recommendation_form";
    }

    @RequestMapping("/user/recommend_offers")
    public String recommend(@ModelAttribute("recomDto") RecommendationsDto recomDto, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("books", recommendationsService.retrieveRecomendations(username,recomDto.getSelectedStrategy()));
        return "user/search_results";
    }
}
