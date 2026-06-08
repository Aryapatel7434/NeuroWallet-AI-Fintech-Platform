package com.smartwallet.integration;

import com.smartwallet.model.User;
import com.smartwallet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

@BeforeEach
void setup() {

    User existingUser =
            userRepository.findByEmail(
                    "integration@test.com"
            );

    if (existingUser == null) {

        User user = new User();

        user.setName("Integration User");
        user.setEmail("integration@test.com");
        user.setPassword(
                passwordEncoder.encode(
                        "123456"
                )
        );
        user.setRole("USER");

        userRepository.save(user);
    }
}
    @Test
    void shouldLoginSuccessfully() throws Exception {

        String json = """
        {
            "email":"integration@test.com",
            "password":"123456"
        }
        """;

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .content(json)
        )
        .andDo(print())
        .andExpect(status().isOk());
    }
}