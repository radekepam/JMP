package com.module1.service;

import com.module1.dao.UserRepository;
import com.module1.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    void getUserByIdShouldReturnUser() {
        //given
        User user = createUser();
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        User result = userService.getUserById(1L);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(user.getName());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void getUserByIdShouldThrowNoSuchElementExceptionWhenUserWithGivenIdNotExist() {
        //given
        when(userRepository.getUserById(1L)).thenReturn(Optional.empty());
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when & then
        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User with given Id not exist");

    }

    @Test
    void getUserByEmailShouldReturnUserWithGivenEmail() {
        //given
        User user = createUser();
        when(userRepository.getUserByEmail("mail")).thenReturn(Optional.of(user));
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        User result = userService.getUserByEmail("mail");

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(user.getName());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void getUserByEmailShouldThrowNoSuchElementExceptionWhenUserWithGivenEmailNotExist() {
        //given
        when(userRepository.getUserByEmail("mail")).thenReturn(Optional.empty());
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when & then
        assertThatThrownBy(() -> userService.getUserByEmail("mail"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User with given email not exist");
    }

    @Test
    void getUsersByNameShouldReturnUsersWithNamesContainingGivenName() {
        //given
        User user = createUser();
        when(userRepository.getUsersByName("name")).thenReturn(List.of(user));
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        List<User> result = userService.getUsersByName("name", 0, 0);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getUsersByNameShouldReturnEmptyListWhenNoneOfNamesContainGivenName() {
        //given
        User user = createUser();
        when(userRepository.getUsersByName("anotherName")).thenReturn(List.of());
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        List<User> result = userService.getUsersByName("anotherName", 0, 0);

        //then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void createUserShouldReturnCreatedUser() {
        //given
        User user = createUser();
        when(userRepository.createUser(any())).thenReturn(user);
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        userService.createUser(user);

        //then
        verify(userRepository, times(1)).createUser(user);
    }

    @Test
    void updateUserShouldReturnUpdatedUser() {
        //given
        User user = createUser();
        when(userRepository.updateUser(any())).thenReturn(user);
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        userService.updateUser(user);

        //then
        verify(userRepository, times(1)).updateUser(user);
    }

    @Test
    void deleteUserShouldReturnTrueWhenUserDeleted() {
        //given
        when(userRepository.deleteById(anyLong())).thenReturn(true);
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        boolean result = userService.deleteUser(1);

        //then
        verify(userRepository, times(1)).deleteById(1);
        assertThat(result).isTrue();
    }

    @Test
    void deleteUserShouldReturnFalseWhenRepositoryDidNotDeleteUser() {
        //given
        when(userRepository.deleteById(anyLong())).thenReturn(false);
        UserService userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        //when
        boolean result = userService.deleteUser(1);

        //then
        verify(userRepository, times(1)).deleteById(1);
        assertThat(result).isFalse();
    }

    private User createUser() {
        return new User(1L, "Name", "mail");
    }
}