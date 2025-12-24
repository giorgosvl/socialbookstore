package myy803.socialbookstore.ControllersTestSuite;
import myy803.socialbookstore.controllers.*;
import myy803.socialbookstore.services.BookRequestsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookRequestsControllerTest {

    @Mock
    private BookRequestsService bookRequestsService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private BookRequestsController bookRequestsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testRequest() {
        int bookId = 1;
        String username = "testUser";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);

        String viewName = bookRequestsController.request(bookId, model);

        verify(bookRequestsService).addRequestingUser(bookId, username);
        assertEquals("redirect:/user/dashboard", viewName);
    }

    @Test
    void testShowUserBookRequests() {
        String username = "testUser";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(bookRequestsService.retrieveRequests(username)).thenReturn( Collections.emptyList());

        String viewName = bookRequestsController.showUserBookRequests(model);

        verify(model).addAttribute("requests",  Collections.emptyList());
        assertEquals("/user/book_requests", viewName);
    }

    @Test
    void testShowRequestingUsersForBookOffer() {
        int bookId = 1;
        when(bookRequestsService.retrieveRequestingUserProfiles(bookId)).thenReturn( Collections.emptyList());

        String viewName = bookRequestsController.showRequestingUsersForBookOffer(bookId, model);

        verify(model).addAttribute("requesting_users",  Collections.emptyList());
        verify(model).addAttribute("book_id", bookId);
        assertEquals("/user/requesting_users", viewName);
    }

    @Test
    void testAcceptRequest() {
        int bookId = 1;
        String selectedUser = "testUser";
        String owner = "ownerUser";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(owner);

        String viewName = bookRequestsController.acceptRequest(selectedUser, bookId, model);

        verify(bookRequestsService).acceptBookRequest(bookId, selectedUser, owner);
        assertEquals("redirect:/user/dashboard", viewName);
    }

    @Test
    void testDeleteBookRequest() {
        int bookId = 1;
        String username = "testUser";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);

        String viewName = bookRequestsController.deleteBookRequest(bookId, model);

        verify(bookRequestsService).deleteBookRequest(bookId, username);
        assertEquals("redirect:/user/dashboard", viewName);
    }
}
