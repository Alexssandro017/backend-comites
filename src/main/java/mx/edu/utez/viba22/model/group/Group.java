package mx.edu.utez.viba22.model.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.viba22.model.member.Member;
import mx.edu.utez.viba22.model.user.User;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "grupo")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroup;

    @NotEmpty
    @Column(length = 50, nullable = false)
    private String name;

    @NotEmpty
    @Column(length = 50, nullable = false)
    private String municipality;

    @NotEmpty
    @Column(length = 50, nullable = false)
    private String colony;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_admin_id")
    @JsonIgnoreProperties(value = {"group"})
    private User groupAdmin;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"group"})
    private List<Member> members;

    public Group(String name, String municipality, String colony, User groupAdmin) {
        this.name = name;
        this.municipality = municipality;
        this.colony = colony;
        this.groupAdmin = groupAdmin;
    }
}