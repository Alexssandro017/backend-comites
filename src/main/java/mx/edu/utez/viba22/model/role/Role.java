package mx.edu.utez.viba22.model.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.viba22.model.user.User;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_role;
@Column(length = 25, nullable = false, unique = true)
    private String nombre;

@JsonIgnoreProperties(value = {"user"})
@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
private List <User> user;

public Role(String nombre){this.nombre = nombre;}
}
