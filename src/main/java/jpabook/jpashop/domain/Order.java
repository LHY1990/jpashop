package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id") //실제 디비의 컬럼 이름
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //실제 디비의 컬럼 이름
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //관련된 엔티티를 연쇄적으로 persist해준다
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간. 하이버네이트가 알아서 관리한다.

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCLE]


    //연관관계 편의 매서드. 컨트롤하는 쪽이 갖고 있는것이 좋다.양방향에서 편의성을 제공한다.
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);

    }

    // 생성매서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비지니스 로직
    //주문취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료 된 상품은 취소가 불가능합니다");
        }
        this.setStatus(OrderStatus.CENCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 조회로직
    // 전체 주문가격 조회
    public int getTotalPrice() {
        int totalPrice = orderItems.stream()
                                    .mapToInt(OrderItem::getTotalPrice)
                                    .sum();
        return totalPrice;
    }
}
