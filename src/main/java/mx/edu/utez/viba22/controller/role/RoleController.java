package mx.edu.utez.viba22.controller.role;

import lombok.Getter;
import mx.edu.utez.viba22.config.ApiResponse;
import mx.edu.utez.viba22.service.role.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){this.roleService = roleService; }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){return roleService.getAll();}
}
