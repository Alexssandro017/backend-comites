package mx.edu.utez.viba22.controller.auth;

import mx.edu.utez.viba22.config.ApiResponse;
import mx.edu.utez.viba22.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signIn(@RequestBody SignDto signDto){
        return authService.signIn(signDto.getEmail(), signDto.getPassword());
    }
}
