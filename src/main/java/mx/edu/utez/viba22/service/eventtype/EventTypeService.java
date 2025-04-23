package mx.edu.utez.viba22.service.eventtype;

import mx.edu.utez.viba22.model.eventtype.EventType;
import mx.edu.utez.viba22.model.eventtype.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public EventType createEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    public Optional<EventType> findEventTypeById(Long id) {
        return eventTypeRepository.findById(id);
    }

    public List<EventType> findAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public Optional<EventType> findEventTypeByName(String name) {
        return eventTypeRepository.findByName(name);
    }

    public EventType updateEventType(Long id, EventType eventTypeDetails) {
        EventType eventType = eventTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EventType not found with id " + id));
        eventType.setName(eventTypeDetails.getName());
        return eventTypeRepository.save(eventType);
    }

    public void deleteEventType(Long id) {
        eventTypeRepository.deleteById(id);
    }
}