package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@DiscriminatorValue("B") //그냥 두면 기본 클래스 이름이 들어간다.
@Entity
public class Book extends Item {
    private String author;
    private String isbn;
}
