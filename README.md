<div align="center">
  <h1>TIL</h1>
</div>

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

#### 제어의 역전(IoC)
- 프로그램의 제어 흐름을 직접 제러하는 것이 아니라 외부에서 관리하는 것이다.

#### 의존관계 주입(DI)
- 인터페이스에 의존하도록 작성하여 실제 어떤 구현 객체가 사용될지는 모르도록 하는 것이다.
- '정적인 클래스 의존관계' & '실행 시점에 결정되는 동적인 객체 의존관계'
  - 정적인 클래스 의존관계
    - 클래스가 사용하는 import문만 보고서 의존관계를 판단할 수 있다.
    - 애플리케이션을 실행하지 않아도 분석이 가능하다.
    - 하지만 이런 의존관계 만으로는 실제로 어떤 구현 객체가 주입될 지는 알 수 없다.
  - 동적인 객체 의존관계
    - 애플리케이션 실행시점에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 의존관계가 연결되는 것을 의미한다.
    - 따라서 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.
    - 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.
    - 정적인 클래스 의존관계를 변경하지 않고 === 클라이언트 코드를 수정하지 않고

#### IoC 컨테이너 & DI 컨테이너
- AppConfig처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 의미
  - 의존관계 주입에 초점을 맞추어 주로 DI 컨테이너라고 부른다.
  - 어샘블러, 오브젝트 팩토리 등으로 불리기도 한다.

#### 스프링 컨테이너
- ApplicationContext를 스프링 컨테이너라고 한다.
- 기존에는 개발자가 AppConfig를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제는 스프링 컨테이너를 통해 사용한다.
- Bean 어노테이션이 붙은 모든 메서드를 호출해서 객체를 등록한다.
- getBean() 메서드를 통해 등록된 객체를 가져올 수 있다.
- 지금부터는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.