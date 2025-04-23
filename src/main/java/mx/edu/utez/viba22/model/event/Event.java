package mx.edu.utez.viba22.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.viba22.model.eventtype.EventType;
import mx.edu.utez.viba22.model.group.Group;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_event;

    @NotEmpty
    @Column(length = 100, nullable = false)
    private String title;

    @NotNull
    private LocalDate date;

    @NotEmpty
    @Column(length = 20, nullable = false)
    private String status; // Próximamente, En ejecución, Finalizado

    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_type_id")
    @JsonIgnoreProperties(value = {"events"})
    private EventType eventType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties(value = {"events"})
    private Group group;

    public Event(String title, LocalDate date, String status, EventType eventType, Group group) {
        this.title = title;
        this.date = date;
        this.status = status;
        this.eventType = eventType;
        this.group = group;
    }
}