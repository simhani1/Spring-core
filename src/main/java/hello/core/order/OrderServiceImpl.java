package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountpolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired private MemberRepository memberRepository;
    @Autowired private DiscountPolicy discountPolicy;  // 추상화된 인터페이스에만 의존하도록 설정, but 구현 객체는 어디서 전달받아야 하는가? -> AppConfig를 통해 생성자 주입을 받으면서 해결된다.

    // setter 주입
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("3, OrderServiceImpl.setMemberRepository");
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("2. OrderServiceImpl.setDiscountPolicy");
//        this.discountPolicy = discountPolicy;
//    }

//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {  // 생성자로 의존성 주입
//        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 싱글톤 객체인지 테스트
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
