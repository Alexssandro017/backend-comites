package mx.edu.utez.viba22.controller.group;

import mx.edu.utez.viba22.model.group.Group;
import mx.edu.utez.viba22.service.group.GroupService;
import mx.edu.utez.viba22.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = {"*"})
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<CustomResponse<Group>> createGroup(@RequestBody Group group) {
        try {
            Group createdGroup = groupService.createGroup(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse<>(createdGroup, "Group created successfully", false, 201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error creating group", true, 500));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<Group>> findGroupById(@PathVariable Long id) {
        try {
            Optional<Group> group = groupService.findGroupById(id);
            return group.map(value -> ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(value, "Group found", false, 200)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Group not found", true, 404)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding group", true, 500));
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Group>>> findAllGroups() {
        try {
            List<Group> groups = groupService.findAllGroups();
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(groups, "Groups found", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding groups", true, 500));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<CustomResponse<Group>> updateGroup(@PathVariable Long id, @RequestBody Group groupDetails) {
        try {
            Group updatedGroup = groupService.updateGroup(id, groupDetails);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(updatedGroup, "Group updated successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error updating group", true, 500));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<CustomResponse<Void>> deleteGroup(@PathVariable Long id) {
        try {
            groupService.deleteGroup(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(null, "Group deleted successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error deleting group", true, 500));
        }
    }
}