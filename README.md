<center>Spring-core</center>
스프링 핵심 원리 - 기본편

<center>TIL</center>

###  2022/12/30

#### 회원 도메인 실행과 테스트
현재 까지의 작업물은 순수 자바로 개발을 한 상태이다.

- 문제점
  - 저장소를 어떤 것을 사용할 지 아직 확정이 안된 상태이다. 따라서 MemberRepository 인터페이스의 구현체를 사용하여 값을 저장한다.<br/>하지만 그 구현체를 생성해줄 뭔가가 없기 때문에 MemberServiceImpl에서 의존관계를 갖게된다.<br/>이는 추후 저장소가 변경된다면 코드 수정이 불가피하므로 OCP, DIP에 위배되는 상황이다.

#### 주문과 할인 도메인 설계
- 주문과 할인 정책
  - 회원은 상품을 주문할 수 있다.
  - 회원 등급에 따라 할인 정책을 적용할 수 있다.
  - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라.
  - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수도 있다.

### 2022/12/31

#### 새로운 할인 정책 개발
- discountPolicy 인터페이스의 구현체를 통해 정책 변경이 가능하다.
  - 하지만 현재 OrderServiceImpl은 추상화된 인터페이스와 구현체에 모두 의존성을 갖고 있다.
  - 따라서 할인 정책을 변경할 때 마다 코드 변경이 발생한다 -> OCP, DIP 위배

#### 해결책: 관심사 분리
- 현재 위와 같은 문제가 발생하는 원인은 OrderServiceImpl에서 SRP를 위배하고 있기 때문이다.
  - 현재 필요한 조치는 관심사를 분리시키는 작업이 필요하다.
  - 즉 OrderServiceImpl 객체는 어떤 할인 정책 구현체를 사용하는지 알 필요가 없도록 만들어야 한다.

- AppConfig 객체를 통해 외부에서 필요한 구현 객체를 주입하도록 한다.
  - 따라서 MemberServiceImpl은 인터페이스에만 의존하고 실제 구현 객체를 어떤걸 사용하는지에 대해서는 전혀 알 필요가 없어지고 외부에서 주입받는 객체를 사용하게 된다.
  - DIP가 완성된 순간이다.
  - 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다. -> 관심사의 분리
  - 공연 기획자라고 생각하자. 배역에 맞는 담당 배우를 선택하는 역할이다.
  - 각 배우들은 담당 기능을 '실행'하는 책임만 지면 된다.

- 새로운 구조와 할인 정책 적용
  - AppConfig의 등장으로 애플리케이션이 크게 '사용 영역'과 객체를 생성하고 '구성(Configuration)하는 영역'으로 분리되었다.
  - 할인 정책을 변경하더라도 AppConfig 내부의 구성 영역만 변경하면 된다.