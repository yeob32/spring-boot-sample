package com.example.security.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class AdminSecurityTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void admin_test() throws Exception {
        mockMvc.perform(get("/api/public/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void menu1_test() throws Exception {
        mockMvc.perform(get("/api/public/menu1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void menu2_test() throws Exception {
        mockMvc.perform(get("/api/public/menu2"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
