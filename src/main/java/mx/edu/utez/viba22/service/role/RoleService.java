package mx.edu.utez.viba22.service.role;

import mx.edu.utez.viba22.config.ApiResponse;
import mx.edu.utez.viba22.model.role.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){this.roleRepository= roleRepository;}

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
return new ResponseEntity <>(new ApiResponse(roleRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }


}
