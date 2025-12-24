package myy803.socialbookstore.ControllersTestSuite;

import myy803.socialbookstore.controllers.RecommendationController;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.RecommendationsDto;
import myy803.socialbookstore.services.RecommendationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecommendationControllerTest {

    @InjectMocks
    private RecommendationController recommendationController;

    @Mock
    private RecommendationsService recommendationsService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController).build();
    }

    @Test
    void testShowRecommendationsForm() throws Exception {
        mockMvc.perform(get("/user/recom"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/recommendation_form"))
                .andExpect(model().attributeExists("recomDto"));
    }

    @Test
    void testRecommend() throws Exception {
        RecommendationsDto recomDto = new RecommendationsDto();
        recomDto.setSelectedStrategy("popular");

        List<BookDto> recommendedBooks = Arrays.asList(new BookDto(), new BookDto());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(recommendationsService.retrieveRecomendations("testuser", "popular")).thenReturn(recommendedBooks);

        mockMvc.perform(post("/user/recommend_offers")
                        .flashAttr("recomDto", recomDto))
                .andExpect(status().isOk())
                .andExpect(view().name("user/search_results"))
                .andExpect(model().attribute("books", recommendedBooks));

        verify(recommendationsService, times(1)).retrieveRecomendations("testuser", "popular");
    }
}
