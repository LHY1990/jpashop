package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;


@Repository
@RequiredArgsConstructor //[2번 선택지]
public class MemberRepository {

//    @PersistenceContext [1번 선택지] //최신 스프링부트 jpa의 경우 @Autowired로도 em을 쓸수있다. 그렇기에 2번선택지를 쓸수있다.
    private final EntityManager em;

//    @PersistenceUnit
//    private EntityManagerFactory emf;
//    이거는 필요 없겠지만 emf를 이렇게 꺼내서 쓸수있다. em을 바로 받기 때문에 필요는 없다.

    //회원을 저장한다.
    public void save(Member member){
        em.persist(member);
    }

    //회원을 id로 호출한다. 단 건 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id); //회원을 id로 호출한다.
    }


    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
