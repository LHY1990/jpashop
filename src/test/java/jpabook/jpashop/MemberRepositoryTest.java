package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//4버전은 @RunWith를 필요로한다. 얘는 5버전
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    @Qualifier("memberRepository")
    MemberRepository memberRepository;

    @Test
    @Transactional //스프링과 javax의 트랜젝션이 있는데 이제는 스프링프레임워크의 것을쓴다.
    @Rollback(value = false) //기본적으로 @Test의 트랜젝션은 모두 rollback된다.이걸 false로 해서 롤백을 막는다.
    public void testMember() throws Exception{

        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());


        //저장할때의 객체와 조회한 후의 객체는 같은 객체이고 주소이다. ==에서 true를 출력
        //같은 트랜젝션 안에서 동작하고, 같은 영속성컨텍스트를 통해 관리되기 때문.
        Assertions.assertThat(findMember).isEqualTo(member);
    }

}