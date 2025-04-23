package mx.edu.utez.viba22.model.user;

import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);


}
