package mx.edu.utez.viba22.controller.member;

import mx.edu.utez.viba22.model.member.Member;
import mx.edu.utez.viba22.service.member.MemberService;
import mx.edu.utez.viba22.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = {"*"})
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN_GROUP_ROLE')")
    public ResponseEntity<CustomResponse<Member>> createMember(@RequestBody Member member) {
        try {
            Member createdMember = memberService.createMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse<>(createdMember, "Member created successfully", false, 201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error creating member", true, 500));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<Member>> findMemberById(@PathVariable Long id) {
        try {
            Optional<Member> member = memberService.findMemberById(id);
            return member.map(value -> ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(value, "Member found", false, 200)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Member not found", true, 404)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding member", true, 500));
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Member>>> findAllMembers() {
        try {
            List<Member> members = memberService.findAllMembers();
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(members, "Members found", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding members", true, 500));
        }
    }

    @GetMapping("/group/{groupId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Member>>> findMembersByGroupId(@PathVariable Long groupId) {
        try {
            List<Member> members = memberService.findMembersByGroupId(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(members, "Members found by group", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding members by group", true, 500));
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE', 'ADMIN_GROUP_ROLE', 'MEMBER_ROLE')")
    public ResponseEntity<CustomResponse<List<Member>>> findMembersByUserId(@PathVariable Long userId) {
        try {
            List<Member> members = memberService.findMembersByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(members, "Members found by user", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error finding members by user", true, 500));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_GROUP_ROLE')")
    public ResponseEntity<CustomResponse<Member>> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        try {
            Member updatedMember = memberService.updateMember(id, memberDetails);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(updatedMember, "Member updated successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error updating member", true, 500));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_GROUP_ROLE')")
    public ResponseEntity<CustomResponse<Void>> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(null, "Member deleted successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(null, "Error deleting member", true, 500));
        }
    }
}