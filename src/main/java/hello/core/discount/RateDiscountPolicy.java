package hello.core.discount;

import hello.core.annotation.MainDiscoutPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscoutPolicy  // 사용자가 직접 등록한 어노테이션을 등록할 수 있다.
// @Qualifier를 사용하다가 오타가 발생할 수 있기 때문에 더욱 안전하게 적용이 가능하다.
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
