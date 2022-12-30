package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountpolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    // Application에 대한 환결설정을 담당하는 클래스
    // 애플리케이션의 실제 동작에 필요한 '구현 객체'를 생성한다.

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    // 다음과 같이 리팩토링을 하면 현재 memberRepository 역할에 어떤 객체가 들어가서 구현을 하는지 명확하게 파악할 수 있게 된다.
    private MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new FixDiscountpolicy();
    }
}
