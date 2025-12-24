package myy803.socialbookstore.services;

import myy803.socialbookstore.datamodel.searchstrategies.SearchFactory;
import myy803.socialbookstore.datamodel.searchstrategies.SearchStrategy;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SearchService {
    @Autowired
    private SearchFactory searchFactory;
    @Autowired
    private BookMapper bookMapper;

    public ArrayList<BookDto> retrieveSearches(SearchDto searchDto){
        SearchStrategy searchStrategy = searchFactory.create(searchDto.getSelectedStrategy());

        if (searchDto.getTitle().equals("") && !Objects.equals(searchDto.getSelectedStrategy(), "All")){
            return new ArrayList<BookDto>();
        }

        return searchStrategy.search(searchDto, bookMapper);
    }

}
