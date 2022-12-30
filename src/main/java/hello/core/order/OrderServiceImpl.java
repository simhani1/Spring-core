package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountpolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // private final DiscountPolicy discountPolicy = new FixDiscountpolicy();  // 할인 정책 변경 시 구현체만 변경하면 된다.
     private final DiscountPolicy discountPolicy = new RateDiscountPolicy();  // 할인 정책 변경은 수월하다. 하지만 현재 Service 코드에 수정이 발생하므로 OCP에 위배된다.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
