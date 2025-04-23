package mx.edu.utez.viba22.controller.eventtype;

import mx.edu.utez.viba22.model.eventtype.EventType;
import mx.edu.utez.viba22.service.eventtype.EventTypeService;
import mx.edu.utez.viba22.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event-types")
@CrossOrigin(origins = {"*"})
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<CustomResponse<EventType>> createEventType(@RequestBody EventType eventType) {
        try {
            EventType createdEventType = eventTypeService.createEventType(eventType);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse<>(createdEventType, "EventType created successfully", false, 201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error creating EventType", true, 500));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<EventType>> findEventTypeById(@PathVariable Long id) {
        try {
            Optional<EventType> eventType = eventTypeService.findEventTypeById(id);
            return eventType.map(value -> ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(value, "EventType found", false, 200)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "EventType not found", true, 404)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding EventType", true, 500));
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<EventType>>> findAllEventTypes() {
        try {
            List<EventType> eventTypes = eventTypeService.findAllEventTypes();
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(eventTypes, "EventTypes found", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding EventTypes", true, 500));
        }
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<EventType>> findEventTypeByName(@PathVariable String name) {
        try {
            Optional<EventType> eventType = eventTypeService.findEventTypeByName(name);
            return eventType.map(value -> ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(value, "EventType found by name", false, 200)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "EventType not found by name", true, 404)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding EventType by name", true, 500));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<CustomResponse<EventType>> updateEventType(@PathVariable Long id, @RequestBody EventType eventTypeDetails) {
        try {
            EventType updatedEventType = eventTypeService.updateEventType(id, eventTypeDetails);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(updatedEventType, "EventType updated successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error updating EventType", true, 500));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<CustomResponse<Void>> deleteEventType(@PathVariable Long id) {
        try {
            eventTypeService.deleteEventType(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(null, "EventType deleted successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error deleting EventType", true, 500));
        }
    }
}