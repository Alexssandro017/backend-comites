package mx.edu.utez.viba22.controller.audit;

import mx.edu.utez.viba22.model.audit.AuditLog;
import mx.edu.utez.viba22.model.audit.AuditLogRepository;
import mx.edu.utez.viba22.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@CrossOrigin(origins = {"*"})
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<CustomResponse<List<AuditLog>>> getAllAuditLogs() {
        try {
            List<AuditLog> logs = auditLogRepository.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CustomResponse<>(logs, "Audit logs retrieved successfully", false, 200));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(null, "Error retrieving audit logs", true, 500));
        }
    }
}
