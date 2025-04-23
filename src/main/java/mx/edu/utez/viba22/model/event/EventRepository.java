package mx.edu.utez.viba22.model.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDate(LocalDate date);

    // Buscar eventos por título (ignorando mayúsculas y minúsculas):
    List<Event> findByTitleContainingIgnoreCase(String title);

    List<Event> findByStatus(String status);
}