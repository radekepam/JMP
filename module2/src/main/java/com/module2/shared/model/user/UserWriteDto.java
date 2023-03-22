package com.module2.shared.model.user;

import com.module2.shared.config.security.ApplicationUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserWriteDto {
    @Size(min = 3, max = 15, message = "Username length should be between 3 and 15")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*\\d).{8,30}$", message = "Password must contain at least one number")
    @Size(min = 8, max = 30, message = "Password should be between 8 and 30 digits")
    private String password;
    @Email(message = "Email format is not valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    public User createUserToSave() {
        return User.builder()
                .username(this.username)
                .password(password)
                .email(this.email)
                .userRole(ApplicationUserRole.USER)
                .isEnabled(false)
                .build();
    }
}
