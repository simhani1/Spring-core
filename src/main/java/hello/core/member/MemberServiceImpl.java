package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
    /**
     * 관심사 분리를 시키기 위해 Repository 객체를 외부에서 주입받아 사용하도록 변경
     * 더이상 DIP를 위배하지 않는다.
     * '생성자 주입'이라고 한다.
     * */
    private final MemberRepository memberRepository;

    // 컴포넌트 스캔을 사용하니 MemberServiceImpl 객체가 자동으로 빈으로 등록되기 때문에 의존관계 주입을 받을 수 없어졌다. 따라서 자동주입을 설정해줘야 한다.
    @Autowired  // ac.getBean(Memberrepository)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 싱글톤 객체인지 테스트
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
