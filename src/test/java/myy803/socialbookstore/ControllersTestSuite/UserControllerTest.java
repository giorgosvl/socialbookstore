package myy803.socialbookstore.ControllersTestSuite;

import myy803.socialbookstore.controllers.*;
import myy803.socialbookstore.services.NotificationService;
import myy803.socialbookstore.services.UserServiceImpl;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.UserProfileDto;
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

class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetUserMainMenu() {
        String viewName = userController.getUserMainMenu();
        assertEquals("user/dashboard", viewName);
    }

    @Test
    void testRetrieveProfile() {
        String username = "testUser";
        UserProfileDto mockProfile = new UserProfileDto();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userServiceImpl.retrieveCategories()).thenReturn(Collections.emptyList());
        when(userServiceImpl.retrieveUserProfile(username)).thenReturn(mockProfile);

        String viewName = userController.retrieveProfile(model);

        verify(model).addAttribute("categories", Collections.emptyList());
        verify(model).addAttribute("profile", mockProfile);
        assertEquals("user/profile", viewName);
    }

    @Test
    void testSaveProfile() {
        UserProfileDto mockProfileDto = new UserProfileDto();

        String viewName = userController.saveProfile(mockProfileDto, model);

        verify(userServiceImpl).saveUserProfile(mockProfileDto);
        assertEquals("user/dashboard", viewName);
    }

    @Test
    void testShowNotifications() {
        String username = "testUser";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(notificationService.retrieveUserNotifications(username)).thenReturn(Collections.emptyList());

        String viewName = userController.showNotifications(model);

        verify(model).addAttribute("notifications", Collections.emptyList());
        assertEquals("user/notifications", viewName);
    }

    @Test
    void testDeleteBookRequest() {
        String username = "testUser";
        int notificationId = 1;
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);

        String viewName = userController.deleteBookRequest(notificationId, model);

        verify(notificationService).removeNotification(notificationId, username);
        assertEquals("redirect:/user/notifications", viewName);
    }
}
