package com.module2.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.module2.shared.config.security.ApplicationUserRole;
import com.module2.shared.model.user.User;
import com.module2.shared.model.user.UserReadDto;
import com.module2.shared.model.user.UserWriteDto;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    void getUserByIdShouldReturnUserForGivenId() throws Exception {
        //given & when
        MvcResult result = mvc.perform(get("/api/users/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        UserReadDto resultUser = objectMapper.readValue(result.getResponse().getContentAsString(), UserReadDto.class);
        assertThat(resultUser.getId()).isEqualTo(1);
        assertThat(resultUser.getEmail()).isEqualTo("User@app.pl");
        assertThat(resultUser.getUsername()).isEqualTo("User");
    }

    @Test
    void getUserByIdShouldReturn400WhenGivenUserNotExist() throws Exception {
        mvc.perform(get("/api/users/5"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("User with given id: 5 not exist")));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserShouldReturnUsersForUserWithAdminRole() throws Exception {
        //given & when
        MvcResult result = mvc.perform(get("/api/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        //then
        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertThat(users).isNotEmpty();
    }

    @Test
    void getUsersShouldReturn401WhenUserHasNoAdminRole() throws Exception {
        //given & when & then
        mvc.perform(get("/api/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    void deleteShouldReturn401WhenUserHasNoAdminRole() throws Exception {
        //given & when & then
        mvc.perform(delete("/api/users/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    @Transactional
    @WithMockUser(roles = "ADMIN")
    void deleteUserShouldReturn200WhenUserWasDeleted() throws Exception {
        mvc.perform(delete("/api/users/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserShouldReturn400WhenUserWithGivenIdNotExist() throws Exception {
        mvc.perform(delete("/api/users/5"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("User with given id: 5 not exist")));
    }

    @Test
    void addUserShouldReturn401WhenUserHasNoAdminRole() throws Exception {
        //given & when & then
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserWriteDto())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(401))
                .andReturn();
    }

    @Test
    @Transactional
    @WithMockUser(roles = "ADMIN")
    void addUserShouldReturn201AndUserReadDtoWhenUserWasAdded() throws Exception {
        //given
        UserWriteDto userWriteDto = createUserWriteDto();

        //when
        MvcResult result = mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWriteDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andReturn();
        //then
        UserReadDto resultUser = objectMapper.readValue(result.getResponse().getContentAsString(), UserReadDto.class);
        assertThat(resultUser.getEmail()).isEqualTo("email");
        assertThat(resultUser.getUsername()).isEqualTo("username");
    }


    private UserWriteDto createUserWriteDto() {
        return new UserWriteDto("username", "password", "email");
    }

    private User createUser() {
        return User.builder()
                .email("mail")
                .username("username")
                .userRole(ApplicationUserRole.USER)
                .password("pass")
                .isEnabled(true)
                .build();
    }
}
