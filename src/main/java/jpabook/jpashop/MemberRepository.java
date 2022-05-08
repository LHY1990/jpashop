package jpabook.jpashop;


import jpabook.jpashop.domain.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//dao랑 비슷한 개념이다. component라서 빈으로 등록된다
@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

//    public Long save(Member member){
//        em.persist(member);
//        return member.getId();
//    }
//
//    public Member find(Long id){
//        return em.find(Member.class, id);
//    }
}
