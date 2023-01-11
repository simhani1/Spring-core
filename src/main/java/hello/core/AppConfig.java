package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountpolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    // Application에 대한 환결설정을 담당하는 클래스
    // 애플리케이션의 실제 동작에 필요한 '구현 객체'를 생성한다.

    /**
     *
     *  @Bean memberService -> new MemoryRepository()
     *  @Bean orderService -> new MemmoryRepository()
     *
     *  이처럼 각 서비스에서 같은 객체를 호출하는데, 호출되는 객체가 싱글톤이 깨지는 것처럼 보인다.
     *  하지만 실제로는 하나의 객체를 사용한다는 것을 확인 할 수 있다.
     * */

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    // 다음과 같이 리팩토링을 하면 현재 memberRepository 역할에 어떤 객체가 들어가서 구현을 하는지 명확하게 파악할 수 있게 된다.
    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
//        return new OrderServiceImpl(
//                memberRepository(),
//                discountPolicy());
        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();  // 할인 정책 변경
    }
}
