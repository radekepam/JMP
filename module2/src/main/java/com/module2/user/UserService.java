package com.module2.user;

import com.module2.shared.model.user.User;
import com.module2.shared.model.user.UserReadDto;
import com.module2.shared.model.user.UserWriteDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserReadDto createUser(UserWriteDto userWriteDTO);

    List<UserReadDto> getAll();

    UserReadDto getUserDtoById(Long id);

    User getUserByUsername(String username);

    void deleteUserById(Long id);
}
