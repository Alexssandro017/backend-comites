package mx.edu.utez.viba22.model.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByGroup_IdGroup(Long id);

    List<Member> findByUser_IdUser(Long id);
}