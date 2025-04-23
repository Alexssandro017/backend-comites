package mx.edu.utez.viba22.controller.user;

import jakarta.validation.Valid;
import mx.edu.utez.viba22.config.ApiResponse;
import mx.edu.utez.viba22.model.user.User;
import mx.edu.utez.viba22.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        return userService.findById(id);
    }
    @PostMapping("/registro/")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody UserDto userDto){
        return userService.register(userDto.toEntity());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        ResponseEntity<ApiResponse> updateResponse = userService.update(id, userDto.toEntity());
        return updateResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        return userService.delete(id);
    }


}
