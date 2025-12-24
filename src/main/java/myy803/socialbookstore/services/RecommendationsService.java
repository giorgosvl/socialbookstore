package myy803.socialbookstore.services;

import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsFactory;
import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsStrategy;
import myy803.socialbookstore.formsdata.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationsService {

    @Autowired
    private RecommendationsFactory recommendationsFactory;

    public List<BookDto> retrieveRecomendations(String username, String recomDto){
        RecommendationsStrategy recommendationsStrategy = recommendationsFactory.create(recomDto);
        return recommendationsStrategy.recommend(username);
    }

}
