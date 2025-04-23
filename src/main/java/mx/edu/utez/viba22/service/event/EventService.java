package mx.edu.utez.viba22.service.event;

import mx.edu.utez.viba22.model.event.Event;
import mx.edu.utez.viba22.model.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> findEventsByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }

    public List<Event> findEventsByTitle(String title) {
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Event> findEventsByStatus(String status) {
        return eventRepository.findByStatus(status);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
        event.setTitle(eventDetails.getTitle());
        event.setDate(eventDetails.getDate());
        event.setStatus(eventDetails.getStatus());
        event.setEventType(eventDetails.getEventType());
        event.setGroup(eventDetails.getGroup());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> findEventsByMemberId(Long userId) {
        return eventRepository.findByMemberId(userId);
    }
}