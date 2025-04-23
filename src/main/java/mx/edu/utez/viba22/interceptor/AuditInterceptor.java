package mx.edu.utez.viba22.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.viba22.model.audit.AuditLog;
import mx.edu.utez.viba22.model.audit.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class AuditInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

        // 1. Método HTTP
        String method = request.getMethod();

        // 2. End-point (URI + query string)
        String endpoint = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        // 3. Timestamp
        LocalDateTime now = LocalDateTime.now();

        // 4. Usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated())
                ? auth.getName()
                : "ANONYMOUS";

        // 5. Guardar en BD
        AuditLog log = new AuditLog();
        log.setHttpMethod(method);
        log.setEndpoint(endpoint);
        log.setTimestamp(now);
        log.setUsername(username);

        auditLogRepository.save(log);
    }
}
