package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;


@Getter
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

//  기본생성자는 있어야 reflection이 가능하다. protected도 지원한다. new로 생성 불가능
    protected Address() {
    }

    //생성자로만 데이터를 입력하도록 한다.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
