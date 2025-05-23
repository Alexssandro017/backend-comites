package mx.edu.utez.viba22.controller.event;

import mx.edu.utez.viba22.model.attendance.Attendance;
import mx.edu.utez.viba22.model.event.Event;
import mx.edu.utez.viba22.model.user.User;
import mx.edu.utez.viba22.model.user.UserRepository;
import mx.edu.utez.viba22.service.attendance.AttendanceService;
import mx.edu.utez.viba22.service.auth.AuthService;
import mx.edu.utez.viba22.service.event.EventService;
import mx.edu.utez.viba22.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = {"*"})
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN_GROUP_ROLE')")
    public ResponseEntity<CustomResponse<Event>> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse<>(createdEvent, "Event created successfully", false, 201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error creating event", true, 500));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<Event>> findEventById(@PathVariable Long id) {
        try {
            Optional<Event> event = eventService.findEventById(id);
            return event.map(value -> ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(value, "Event found", false, 200)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Event not found", true, 404)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding event", true, 500));
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE')")
    public ResponseEntity<CustomResponse<List<Event>>> findAllEvents() {
        try {
            List<Event> events = eventService.findAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(events, "Events found", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding events", true, 500));
        }
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Event>>> findEventsByDate(@PathVariable LocalDate date) {
        try {
            List<Event> events = eventService.findEventsByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(events, "Events found by date", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding events by date", true, 500));
        }
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Event>>> findEventsByTitle(@PathVariable String title) {
        try {
            List<Event> events = eventService.findEventsByTitle(title);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(events, "Events found by title", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding events by title", true, 500));
        }
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Event>>> findEventsByStatus(@PathVariable String status) {
        try {
            List<Event> events = eventService.findEventsByStatus(status);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(events, "Events found by status", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding events by status", true, 500));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_GROUP_ROLE')")
    public ResponseEntity<CustomResponse<Event>> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(updatedEvent, "Event updated successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error updating event", true, 500));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_GROUP_ROLE')")
    public ResponseEntity<CustomResponse<Void>> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(null, "Event deleted successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error deleting event", true, 500));
        }
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Event>>> findMyEvents(Authentication auth) {
        try {
            // 1) Tomamos el username (no-email si tu JWT lo usa así)
            String username = auth.getName();

            // 2) Buscamos al User para obtener el idUser
            User me = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

            // 3) Recuperamos solo los eventos de los grupos donde es miembro
            List<Event> myEvents = eventService.findEventsByMemberId(me.getIdUser());

            return ResponseEntity.ok(new CustomResponse<>(myEvents,
                    "Eventos del miembro recuperados exitosamente",
                    false, 200));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(null, "Error al recuperar tus eventos", true, 500));
        }
    }

    @PostMapping("/{id}/attendance")
    @PreAuthorize("hasAuthority('MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<Attendance>> confirmAttendance(
            @PathVariable Long id,
            Authentication auth) {
        try {
            Attendance a = attendanceService.confirmAttendance(id, auth.getName());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(a, "Asistencia confirmada", false, 201));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CustomResponse<>(null, e.getMessage(), true, 400));
        }
    }
}
