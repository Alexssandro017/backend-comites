package mx.edu.utez.viba22.model.eventtype;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.viba22.model.event.Event;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event_type")
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_event_type;

    @NotEmpty
    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "eventType", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"eventType"})
    private List<Event> events;

    public EventType(String name) {
        this.name = name;
    }
}