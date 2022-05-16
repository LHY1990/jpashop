package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    //@Rollback(false) @Test의 경우 @Transactional이 작업 후 rollback 시켜버린다.
    //그런경우 jpa가 디비에 트랜젝션을 보내지도 않는다. 이 경우 해당 EntityManager를 호출해서
    // .flush()를 날리면 쿼리가 출력된다.
    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test //(expected = IllegalStateException.class) https://www.inflearn.com/questions/42746
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim1");
        Member member2 = new Member();
        member2.setName("kim1");
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return;
        }
        //then
//        fail("예외가 발생해야한다."); assertion

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2))


    }



}