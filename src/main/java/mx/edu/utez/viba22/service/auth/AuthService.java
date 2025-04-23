package mx.edu.utez.viba22.service.auth;

import mx.edu.utez.viba22.config.ApiResponse;
import mx.edu.utez.viba22.controller.auth.SignedDto;
import mx.edu.utez.viba22.model.user.User;
import mx.edu.utez.viba22.security.jwt.JwtProvider;
import mx.edu.utez.viba22.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {
    private final UserService userService;

    private final AuthenticationManager manager;
    private final JwtProvider provider;

    public AuthService(UserService userService, AuthenticationManager manager, JwtProvider provider) {
        this.userService = userService;
        this.manager = manager;
        this.provider = provider;
    }
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> signIn(String email, String password){
        try {
            Optional<User> foundUser = userService.findByEmail(email);
            if (foundUser.isEmpty())
                return new ResponseEntity<>(new ApiResponse(
                        HttpStatus.NOT_FOUND, true, "No se encontró el correo"
                ), HttpStatus.NOT_FOUND);


            User user = foundUser.get();
            System.out.println(user);
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = provider.generateToken(auth);

            SignedDto signedDto = new SignedDto(token, "Bearer", user, user.getRole());
            return new ResponseEntity<>(new ApiResponse(signedDto, HttpStatus.OK), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String message = "CredentialMismatch";
            if (e instanceof DisabledException)
                message = "UserDisabled";
            return new ResponseEntity<>(
                    new ApiResponse(HttpStatus.BAD_REQUEST, true, message),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}