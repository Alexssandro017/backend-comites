// Busca asistencia (no obligatorio, pero recomendable)
package mx.edu.utez.viba22.model.attendance;
import mx.edu.utez.viba22.model.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
