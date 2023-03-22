package com.module2.user;

import com.module2.shared.config.security.ApplicationUserRole;
import com.module2.shared.error.ErrorMessage;
import com.module2.shared.error.errors.EmailExistException;
import com.module2.shared.error.errors.UserNotExistException;
import com.module2.shared.error.errors.UsernameExistException;
import com.module2.shared.model.user.User;
import com.module2.shared.model.user.UserReadDto;
import com.module2.shared.model.user.UserWriteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private static final String USERNAME = "testUser";
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
    }

    @Test
    void createUserShouldCreateNewUser() {
        // given
        UserWriteDto userWriteDTO = getUserWriteDtoToSave();
        User savedUser = new User(USERNAME, "testemail", "testpassword", ApplicationUserRole.USER, true);
        savedUser.setId(1L);

        when(userRepository.existsByEmailIgnoreCase(userWriteDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase(userWriteDTO.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserService userService = new UserServiceImpl(userRepository);

        // when
        UserReadDto result = userService.createUser(userWriteDTO);

        // then
        verify(userRepository).save(any(User.class));
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo(USERNAME);
        assertThat(result.getEmail()).isEqualTo("testemail");
    }

    @Test
    void createUserShouldThrowUsernameExistExceptionWhenUserWithGivenUsernameExist() {
        //given
        var userToSave = getUserWriteDtoToSave();
        when(userRepository.existsByUsernameIgnoreCase(userToSave.getUsername())).thenReturn(true);
        UserService userService = new UserServiceImpl(userRepository);

        //when and then
        assertThatThrownBy(() -> userService.createUser(userToSave))
                .isInstanceOf(UsernameExistException.class)
                .hasMessage(ErrorMessage.USER_EXIST_EXCEPTION.getMessage());
    }

    @Test
    void createUserShouldThrowEmailExistExceptionWhenUserWithGivenEmailExist() {
        //given
        var userToSave = getUserWriteDtoToSave();
        when(userRepository.existsByUsernameIgnoreCase(userToSave.getUsername())).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase(userToSave.getEmail())).thenReturn(true);
        UserService userService = new UserServiceImpl(userRepository);

        //when and then
        assertThatThrownBy(() -> userService.createUser(userToSave))
                .isInstanceOf(EmailExistException.class)
                .hasMessage(ErrorMessage.EMAIL_EXIST_EXCEPTION.getMessage());
    }

    @Test
    void deleteUserByIdShouldDeleteUserIfExists() {
        //given
        when(userRepository.existsById(anyLong())).thenReturn(true);
        UserService userService = new UserServiceImpl(userRepository);

        //when
        userService.deleteUserById(1L);

        //then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUserByIdShouldThrowUserNotExistExceptionWhenUserNotExist() {
        //given
        when(userRepository.existsById(anyLong())).thenReturn(false);
        UserService userService = new UserServiceImpl(userRepository);

        //when and then
        assertThatThrownBy(() -> userService.deleteUserById(1L)).isInstanceOf(UserNotExistException.class).hasMessage("User with given id: %d not exist", 1);
    }

    @Test
    void getAllShouldReturnListOfUserReadDto() {
        //given
        User user = new User(USERNAME, "testemail", "testpassword", ApplicationUserRole.USER, false);
        user.setId(1L);
        when(userRepository.findAll()).thenReturn(List.of(user));
        UserService userService = new UserServiceImpl(userRepository);

        //when
        List<UserReadDto> result = userService.getAll();

        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void getUserDtoByIdShouldReturnUserReadDtoIfUserExists() {
        //given
        User user = new User(USERNAME, "testemail", "testpassword", ApplicationUserRole.USER, false);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserService userService = new UserServiceImpl(userRepository);

        //when
        UserReadDto result = userService.getUserDtoById(1L);

        //then
        assertThat(result.getUsername()).isEqualTo(USERNAME);
        assertThat(result.getEmail()).isEqualTo("testemail");
    }


    @Test
    void getUserDtoByIdShouldThrowUserNotExistExceptionWhenUserWithGivenIdNotExist() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        UserService userService = new UserServiceImpl(userRepository);

        //when and then
        assertThatThrownBy(() -> userService.getUserDtoById(1L))
                .isInstanceOf(UserNotExistException.class)
                .hasMessage("User with given id: %d not exist", 1);
    }


    @Test
    void getUserByUsernameShouldReturnUserIfUserExists() {
        //given
        User user = new User(USERNAME, "testemail", "testpassword", ApplicationUserRole.USER, false);
        when(userRepository.findByUsernameIgnoreCase(USERNAME)).thenReturn(Optional.of(user));
        UserService userService = new UserServiceImpl(userRepository);

        //when
        User result = userService.getUserByUsername(USERNAME);

        //then
        assertThat(result.getUsername()).isEqualTo(USERNAME);
        assertThat(result.getEmail()).isEqualTo("testemail");
    }

    @Test
    void getUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserWithGivenUsernameNotExist() {
        //given
        when(userRepository.findByUsernameIgnoreCase(USERNAME)).thenReturn(Optional.empty());
        UserService userService = new UserServiceImpl(userRepository);

        //when and then
        assertThatThrownBy(() -> userService.getUserByUsername(USERNAME))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with given username %s not exist", USERNAME);
    }


    @Test
    void loadUserByUsernameShouldReturnUserDetailsIfUserExists() {
        //given
        User user = new User(USERNAME, "testemail", "testpassword", ApplicationUserRole.USER, false);
        when(userRepository.findByUsernameIgnoreCase(USERNAME)).thenReturn(Optional.of(user));
        UserService userService = new UserServiceImpl(userRepository);

        //when
        UserDetails result = userService.loadUserByUsername(USERNAME);

        //then
        assertThat(result.getUsername()).isEqualTo(USERNAME);
    }

    private UserWriteDto getUserWriteDtoToSave() {
        return new UserWriteDto(USERNAME, "testpassword", "testemail");
    }
}