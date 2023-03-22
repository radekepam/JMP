package com.module2.user;

import com.module2.shared.config.security.CustomPasswordEncoder;
import com.module2.shared.error.ErrorMessage;
import com.module2.shared.error.errors.EmailExistException;
import com.module2.shared.error.errors.UserNotExistException;
import com.module2.shared.error.errors.UsernameExistException;
import com.module2.shared.model.user.User;
import com.module2.shared.model.user.UserReadDto;
import com.module2.shared.model.user.UserWriteDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserReadDto createUser(UserWriteDto userWriteDTO) {
        if (userRepository.existsByUsernameIgnoreCase(userWriteDTO.getUsername())) {
            throw new UsernameExistException(ErrorMessage.USER_EXIST_EXCEPTION.getMessage());
        }
        if (userRepository.existsByEmailIgnoreCase(userWriteDTO.getEmail())) {
            throw new EmailExistException(ErrorMessage.EMAIL_EXIST_EXCEPTION.getMessage());
        }
        User userToSave = userWriteDTO.createUserToSave();
        userToSave.setPassword(CustomPasswordEncoder.encodePassword(userToSave.getPassword()));
        User savedUser = userRepository.save(userToSave);
        return new UserReadDto(savedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
        } else {
            throw new UserNotExistException(String.format("User with given id: %d not exist", id));
        }
    }

    @Override
    public List<UserReadDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserReadDto::new)
                .toList();
    }

    @Override
    public UserReadDto getUserDtoById(Long id) {
        return userRepository.findById(id)
                .map(UserReadDto::new)
                .orElseThrow(() -> new UserNotExistException(String.format("User with given id: %d not exist", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return getUserByUsernameFromRepo(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getUserByUsernameFromRepo(username);
    }

    private User getUserByUsernameFromRepo(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with given username %s not exist", username)));
    }
}
