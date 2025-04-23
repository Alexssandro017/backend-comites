package mx.edu.utez.viba22.controller.auth;

import lombok.Value;
import mx.edu.utez.viba22.model.role.Role;
import mx.edu.utez.viba22.model.user.User;

@Value
public class SignedDto {
    String token;
    String tokenType;
    User user;
    Role role;
}
