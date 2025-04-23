package mx.edu.utez.viba22.model.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.edu.utez.viba22.model.member.Member;


import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByGroup_IdGroup(Long id);

    List<Member> findByUser_IdUser(Long id);

    Optional<Member> findByUser_IdUserAndGroup_IdGroup(Long userId, Long groupId);

}