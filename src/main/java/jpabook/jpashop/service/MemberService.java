package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@AllArgsConstructor 이거도 좋지만
@Service
@Transactional(readOnly = true) //트랜젝션 안에서 작동해야한다. 모든 로직은. javax보단 springboot의 트랜젝션을 쓰자. 이렇게 위에서 해도되지만. 각 매서드에서 할수있다.
@RequiredArgsConstructor //이거를 더 추천한다.[4번 선택지]. 2=<1<3<4 4번이 제일좋다
public class MemberService {

//  @Autowired [1번 선택지]// 위에 @RequiredArgsConstructor 가 autowired를 해결해준다.
    private final MemberRepository memberRepository; //여기에 파이널을 넣는것을 추천한다.
    
//    @Autowired[2번 선택지]
//    public void seetMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    } 이 방식도 있으나 문제가 많다. setter 객체 인젝션 이걸 대체하는 좋은것이 생성자 인젝션(바로아래)이다

//    [3번 선택지]
//    @Autowired //이렇게 하는것이 좀더 좋다. 요즘 스프링은 이런경우 어노테이션 없어도 자동으로 해준다.
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    //회원가입
    @Transactional //readonly 의 기본값은 false이다. 맨위 클래스에 넣고 이렇게 매서드 위에 어노테이션을 놓으면 오버라이딩된다. 지역변수느낌 이렇게 쓰는걸 권장한다.
    public Long join(Member member) {
        validateDuplicateMember(member); //중복검사
        memberRepository.save(member);
        return member.getId();
    }

    //중복회원검색
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    //회원전체조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}

