package myy803.socialbookstore.ControllersTestSuite;

import myy803.socialbookstore.controllers.BookOffersController;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.services.BookOffersService;
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

class BookOffersControllerTest {

    @InjectMocks
    private BookOffersController bookOffersController;

    @Mock
    private BookOffersService bookOffersService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookOffersController).build();
    }

    @Test
    void testListBookOffers() throws Exception {
        List<BookDto> bookOffers = Arrays.asList(new BookDto(), new BookDto());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(bookOffersService.retrieveBookOffers("testuser")).thenReturn(bookOffers);

        mockMvc.perform(get("/user/offers"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/offers"))
                .andExpect(model().attribute("offers", bookOffers));
        verify(bookOffersService, times(1)).retrieveBookOffers("testuser");
    }

    @Test
    void testShowOfferForm() throws Exception {
        mockMvc.perform(get("/user/show_offer_form"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/offer-form"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("offer"));
    }

    @Test
    void testSaveOffer() throws Exception {
        BookDto bookOfferDto = new BookDto();
        bookOfferDto.setTitle("Test Book");
        bookOfferDto.setAuthors("Test Author");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(post("/user/save_offer")
                        .flashAttr("offer", bookOfferDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/offers"));
        verify(bookOffersService, times(1)).saveBookOffer(bookOfferDto, "testuser");
    }

    @Test
    void testDeleteBookOffer() throws Exception {
        int bookId = 1;

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user/delete_book_offer")
                        .param("selected_offer_id", String.valueOf(bookId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));

        verify(bookOffersService, times(1)).deleteBookOffer(bookId, "testuser");
    }
}
