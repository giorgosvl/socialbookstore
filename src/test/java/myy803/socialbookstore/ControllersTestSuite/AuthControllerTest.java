package myy803.socialbookstore.ControllersTestSuite;

import myy803.socialbookstore.controllers.AuthController;
import myy803.socialbookstore.datamodel.User;
import myy803.socialbookstore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    void testRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        when(userService.isUserPresent(user)).thenReturn(true);


        mockMvc.perform(post("/save")
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(model().attribute("successMessage", "User already registered!"));

        verify(userService, times(1)).isUserPresent(user);
    }

    @Test
    void testRegisterUser_SuccessfulRegistration() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password123");

        when(userService.isUserPresent(user)).thenReturn(false);

        mockMvc.perform(post("/save")
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(model().attribute("successMessage", "User registered successfully!"));
        verify(userService, times(1)).saveUser(user);
    }
}
