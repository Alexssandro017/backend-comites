package mx.edu.utez.viba22.service.attendance;

import mx.edu.utez.viba22.model.attendance.Attendance;
import mx.edu.utez.viba22.model.event.Event;
import mx.edu.utez.viba22.model.member.Member;
import mx.edu.utez.viba22.model.user.User;
import mx.edu.utez.viba22.model.attendance.AttendanceRepository;
import mx.edu.utez.viba22.model.event.EventRepository;
import mx.edu.utez.viba22.model.member.MemberRepository;
import mx.edu.utez.viba22.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired private AttendanceRepository attendanceRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private UserRepository userRepository;

    public Attendance confirmAttendance(Long eventId, String username) {
        // 1) coger el usuario
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        // 2) coger el evento
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado: " + eventId));

        // 3) validar que el user sea miembro de ese grupo
        Member member = memberRepository
                .findByUser_IdUserAndGroup_IdGroup(user.getIdUser(), event.getGroup().getIdGroup())
                .orElseThrow(() -> new RuntimeException("No eres miembro del grupo de este evento"));

        // 4) crear y guardar Attendance
        Attendance attendance = new Attendance(event, member);
        return attendanceRepository.save(attendance);
    }
}
