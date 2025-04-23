package mx.edu.utez.viba22.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.viba22.model.role.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Column(length = 50, nullable = false)
    private String name;
    @Column (length = 40, nullable = false)
    private String email;
    @Column(length = 150, nullable = false)
    private String password;
    @Column(length = 30, nullable = false)
    private String phone;
    @Column(length = 20, nullable = true)
    private String avatar;
    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean status; //no

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties(value = {"user"})
    private Role role;


    public User(Long id_user, String name, String email, String password, String phone, String avatar, Boolean status) {
        this.idUser = id_user;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
        this.status= status;
    }

    public User(Long id_user, String name, String email, String password, String phone, String avatar, Boolean status, Role role) {
        this.idUser = id_user;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
        this.status = status;
        this.role = role;
    }

    public User(String name, String email, String password, Boolean status, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.phone = phone;
    }
}

