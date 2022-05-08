package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //내장타입
    private Address address;

    @OneToMany(mappedBy = "member") //Order의 member 필드이다.
    private List<Order> orders = new ArrayList<>();
//    @JoinColumn(name = "member_id")


}
