package mx.edu.utez.viba22.service.user;

import mx.edu.utez.viba22.config.ApiResponse;
import mx.edu.utez.viba22.model.role.Role;
import mx.edu.utez.viba22.model.role.RoleRepository;
import mx.edu.utez.viba22.model.user.User;
import mx.edu.utez.viba22.model.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public ResponseEntity <ApiResponse> getAll(){
return new ResponseEntity<>(new ApiResponse(userRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(User usuario) {
        System.out.println(usuario.toString());
        Optional<User> foundUsuario = userRepository.findByEmail(usuario.getEmail());
        if (foundUsuario.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,
                    true, "EmailAlreadyExist"),
                    HttpStatus.BAD_REQUEST);

        usuario.setStatus(true);


        Optional<Role> roleOpt = roleRepository.findById(usuario.getRole().getId_role());
        if (roleOpt.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Rol no encontrado"), HttpStatus.NOT_FOUND);
        }else {
            System.out.println("xd");
        }
        usuario.setRole(roleOpt.get());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario = userRepository.saveAndFlush(usuario);

        return new ResponseEntity<>(new ApiResponse(
                usuario,
                HttpStatus.OK
        ), HttpStatus.OK);
}

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        Optional<User> findById = userRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el Id"),
                    HttpStatus.NOT_FOUND);
        userRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Usuario encontrado"), HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<User> findById = userRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"),
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(userRepository.findById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(Long id, User updatedUsuario) {
        Optional<User> optionalUsuario = userRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            User user = optionalUsuario.get();
            user.setName(updatedUsuario.getName());
            user.setEmail(updatedUsuario.getEmail());
            user.setPassword(updatedUsuario.getPassword());
            user.setPhone(updatedUsuario.getPhone());
            user.setAvatar(updatedUsuario.getAvatar());


            userRepository.save(user);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Usuario actualizado"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el usuario con el ID proporcionado"), HttpStatus.NOT_FOUND);
        }
    }



}
