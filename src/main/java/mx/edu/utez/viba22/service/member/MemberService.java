package mx.edu.utez.viba22.service.member;

import mx.edu.utez.viba22.model.member.Member;
import mx.edu.utez.viba22.model.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> findMembersByGroupId(Long groupId) {
        return memberRepository.findByGroup_IdGroup(groupId);
    }

    public List<Member> findMembersByUserId(Long userId) {
        return memberRepository.findByUser_IdUser(userId);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + id));
        member.setUser(memberDetails.getUser());
        member.setGroup(memberDetails.getGroup());
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}