package com.hnjee.jemilog.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) //디폴트 설정을 true로 하고 조회 아닌 곳은 false 설정
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional //readonly 디폴트 false
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 아이디 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> foundMembers = memberRepository.findByLoginId(member.getLoginId());
        if(!foundMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 아이디입니다");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 아이디로 조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
