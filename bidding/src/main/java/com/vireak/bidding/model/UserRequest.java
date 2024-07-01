package com.vireak.bidding.model;

import com.vireak.bidding.enums.Provider;
import com.vireak.bidding.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {
    @NotBlank(message = "First Name can't blank")
    private String firstName;
    @NotBlank(message = "Last Name can't blank")
    private String lastName;
    @Email(message = "Email not well format")
    private String email;
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String matchingPassword;
    private Role role;
    private Provider provider=Provider.AUTHORIZATION_SERVER;
}
