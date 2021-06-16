## 컴포지트 패턴(Composite pattern)
>객체들의 관계를 트리 구조로 구성하여 전체-부분(Ex. Directory-File) 계층을 표현하는 패턴으로,  
사용자가 단일 객체와 복합 객체 모두 동일하게 다루도록 한다.

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Composite_UML_class_diagram_%28fixed%29.svg/480px-Composite_UML_class_diagram_%28fixed%29.svg.png)


<img width="450" alt="99E9FF455C84AF1E20" src="https://user-images.githubusercontent.com/4969393/122172992-42833280-cebc-11eb-8694-d9114a7f875b.png">

<img width="450" alt="9923A84E5C84B5203A" src="https://user-images.githubusercontent.com/4969393/122173013-46af5000-cebc-11eb-9c99-054edc474115.png">

- Component : 모든 표현할 요소들의 추상적인 인터페이스이다.
- Leaf : Component 인터페이스를 구현하고 구현체 클래스를 나타낸다. (단일 객체)
- Composit : Component 인터페이스를 구현하고, 구현되는 자식들을 가지고 관리하기 위한 메소드(addChild, removeChild..)를 구현한다.  
또한, 일반적으로 인터페이스에 작성된 메소드는 자식에게 위임하는 처리를 한다. (복합 객체)

### 활용
>
