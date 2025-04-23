package mx.edu.utez.viba22.controller.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.viba22.model.role.Role;
import mx.edu.utez.viba22.model.user.User;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id_user;
    private String name;
    private String email;
    private String password;
    private String phone ;
    private String avatar;
    private Boolean status;
    private Role role;

    public User toEntity(){
        if(role == null)
            return new User(id_user, name, email, password, phone, avatar, status);
return new User(id_user, name, email, password, phone, avatar, status, role);
    }


}
