package bg.softuni.campingcars.web;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(this.port, this.host, "smtp"));

        greenMail.start();
        greenMail.setUser(this.username, this.password);
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void testRegistration() throws Exception {

        mvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .param("email", "test@example.com")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .param("age", "25")
                        .with(csrf())

        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        assertEquals(1, receivedMessages.length);

        MimeMessage registrationMessage = receivedMessages[0];

        assertTrue(registrationMessage.getContent().toString().contains("Petar"));
        assertEquals(1, registrationMessage.getAllRecipients().length);
        assertEquals("test@example.com", registrationMessage.getAllRecipients()[0].toString());
    }

}