package mx.edu.utez.viba22.config;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.viba22.model.role.Role;
import mx.edu.utez.viba22.model.role.RoleRepository;
import mx.edu.utez.viba22.model.user.User;
import mx.edu.utez.viba22.model.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Configuration
public class InitialConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public InitialConfig(RoleRepository roleRepository, UserRepository usuarioRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        Role adminRole = getOrSaveRole(new Role("ADMIN_ROLE"));
        Role adminGroupRole = getOrSaveRole(new Role("ADMIN_GROUP_ROLE"));
        Role memberRole = getOrSaveRole(new Role("MEMBER_ROLE"));

        // Crear usuarios y asignarles roles
        User oscar = getOrSaveUser(
                new User("oscar", "oscar@gmail.com", encoder.encode("oscar123"), true, "777453837")
        );
        saveUserRoles(oscar.getIdUser(), adminRole.getId_role());

        User eliam = getOrSaveUser(
                new User("eliam", "eliam@gmail.com", encoder.encode("eliam123"), true, "555123456")
        );
        saveUserRoles(eliam.getIdUser(), adminGroupRole.getId_role());

        User isaac = getOrSaveUser(
                new User("isaac", "isaac@gmail.com", encoder.encode("isaac123"), true, "555654321")
        );
        saveUserRoles(isaac.getIdUser(), memberRole.getId_role());
    }

    @Transactional
    public Role getOrSaveRole(Role role) {
        Optional<Role> foundRole = roleRepository.findByNombre(role.getNombre());
        return foundRole.orElseGet(() -> {
            roleRepository.saveAndFlush(role);
            return roleRepository.findByNombre(role.getNombre()).orElse(null);
        });
    }

    @Transactional
    public User getOrSaveUser(User user) {
        Optional<User> foundUser = usuarioRepository.findByEmail(user.getEmail());
        return foundUser.orElseGet(() -> {
            Role userRole = user.getRole();
            if (userRole != null) {
                if (userRole.getId_role() == null) {
                    userRole = getOrSaveRole(userRole);
                }
            }
            user.setRole(userRole);
            return usuarioRepository.saveAndFlush(user);
        });
    }

    @Transactional
    public void saveUserRoles(Long userId, Long roleId) {
        User usuario = usuarioRepository.findById(userId).orElse(null);
        if (usuario != null) {
            Role newRole = roleRepository.findById(roleId).orElse(null);
            if (newRole != null) {
                usuario.setRole(newRole);
                usuarioRepository.save(usuario);
            }
        }
    }
}