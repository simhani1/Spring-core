package hello.core.member;

public class MemberServiceImpl implements MemberService {
    /**
     * 관심사 분리를 시키기 위해 Repository 객체를 외부에서 주입받아 사용하도록 변경
     * 더이상 DIP를 위배하지 않는다.
     * '생성자 주입'이라고 한다.
     * */
    private final MemberRepository memberRepository;

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
