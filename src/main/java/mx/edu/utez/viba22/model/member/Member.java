package mx.edu.utez.viba22.model.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.viba22.model.group.Group;
import mx.edu.utez.viba22.model.user.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"members"})
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties(value = {"members"})
    private Group group;

    public Member(User user, Group group) {
        this.user = user;
        this.group = group;
    }
}