package myy803.socialbookstore.ControllersTestSuite;

import myy803.socialbookstore.controllers.SearchController;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.services.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SearchControllerTest {

    @InjectMocks
    private SearchController searchController;

    @Mock
    private SearchService searchService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    void testShowSearchForm() throws Exception {
        mockMvc.perform(get("/user/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/search_form"))
                .andExpect(model().attributeExists("searchDto"));
    }

    @Test
    void testSearch() throws Exception {
        SearchDto searchDto = new SearchDto();
        searchDto.setTitle("Test Book");

        List<BookDto> searchResults = new ArrayList<>();
        searchResults.add(new BookDto());
        searchResults.add(new BookDto());

        when(searchService.retrieveSearches(searchDto)).thenReturn((ArrayList<BookDto>) searchResults);

        mockMvc.perform(post("/user/search_offer")
                        .flashAttr("searchDto", searchDto))
                .andExpect(status().isOk())
                .andExpect(view().name("user/search_results"))
                .andExpect(model().attribute("books", searchResults));

        verify(searchService, times(1)).retrieveSearches(searchDto);
    }
}
