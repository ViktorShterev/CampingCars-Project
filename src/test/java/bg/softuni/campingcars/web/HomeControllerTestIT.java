package bg.softuni.campingcars.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homePage() throws Exception {

        mockMvc.perform(
                get("/")
                        .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    void about() throws Exception {

        mockMvc.perform(
                get("/about")
                        .with(csrf())
        ).andExpect(status().isOk());
    }
}