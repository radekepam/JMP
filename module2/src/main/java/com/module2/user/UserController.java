package com.module2.user;

import com.module2.shared.model.user.UserReadDto;
import com.module2.shared.model.user.UserWriteDto;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Timed(value = "module2_custom_timer", description = "Timer for test")
    @GetMapping("/test")
    public String test() {
        logger.info("Test was invoked");
        return "test";
    }
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserReadDto> getUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserDtoById(userId));
    }

    @GetMapping(path = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserReadDto>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        this.userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserReadDto> addUser(@RequestBody UserWriteDto userToRegister) {
        UserReadDto createdUser = userService.createUser(userToRegister);
        return ResponseEntity
                .created(URI.create("/api/users/" + createdUser.getId()))
                .body(createdUser);
    }
}
