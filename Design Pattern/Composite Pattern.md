## 컴포지트 패턴(Composite pattern)
>객체들의 관계를 트리 구조로 구성하여 부분-전체(Ex. Directory-File) 계층을 표현하는 패턴으로,  
사용자가 단일 객체와 복합 객체 모두 동일하게 다루도록 한다.

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Composite_UML_class_diagram_%28fixed%29.svg/480px-Composite_UML_class_diagram_%28fixed%29.svg.png)


<img width="450" alt="99E9FF455C84AF1E20" src="https://user-images.githubusercontent.com/4969393/122172992-42833280-cebc-11eb-8694-d9114a7f875b.png">

<img width="450" alt="9923A84E5C84B5203A" src="https://user-images.githubusercontent.com/4969393/122173013-46af5000-cebc-11eb-9c99-054edc474115.png">

- Component : Leaf와 Composite 가 구현해야하는 Interface 로, Leaf 와 Composite 는 모두 Component 라는 같은 타입으로 다뤄진다.
- Leaf : Component 인터페이스를 구현하고, 단일 객체로ㅅ Component의 형태로 Composite 의 부분(자식) 객체로 들어가게 된다.
- Composit : Component 인터페이스를 구현하고, 구현되는 자식들을 가지고 관리하기 위한 메소드(addChild, removeChild..)를 구현한다.  
집합 객체로 Component의 형태로서 Leaf 객체나 Composite 를 부분(자식)으로 둔다.  
또한, 일반적으로 인터페이스에 작성된 메소드는 자식에게 위임하는 처리를 한다.  
클라이언트는 이 Composit 을 통ㅎ 부분 객체들(Leaf, Composit) 을 다룰 수 있다.

### 활용
- 객체들간에 계급 및 계층구조가 있고 이를 표현해야할 때. Tree 구조 (Directory-File, 회사-각 부서사원)
- 클라이언트가 단이 객체와 집합 객체를 구분하지 않고 동일한 형태 사용하고자 할 때
- 

예제
```
// `Component`
interface Node {
    public String getName();
}

// `Composit`
class Directory implements Node {
    private String name;
    private List<Node> children;
    // ...
    @Override
    public String getName(){ return name; }
    public void add(Node node) {
        children.add(node);
    }
}

// `Leaf`
class File implements Node {
    private String name;
    // ...
    @Override
    public String getName(){ return name; }
}

// `Client`
class Exam {
  public static void main(String[] args) {
    Directory dir = new Directory();
    dir.add(new File());
    dir.add(new Directory());
    Directory root = new Directory();
    root.add(dir);
  }
}
```

tmp
```

fun main(args: Array<String>) {
    val lab = Department("연구실")
    val mobileTeam = Department("마케팅 팀")

    mobileTeam.apply {
        addChild(Employee("일길동"))
        addChild(Employee("이길동"))
    }

    lab.addChild(mobileTeam)
    lab.printName()
}

// Component
abstract class Node {
    abstract fun getName(): String
}

// Leaf
class Employee(name: String) : Node() {
    private val empName = name
    override fun getName(): String {
        return empName
    }
}

// Composite
class Department(name: String) : Node() {
    private val list: ArrayList<Node> = ArrayList()
    private val depName = name

    override fun getName(): String {
        return depName
    }

    fun addChild(item: Node) {
        list.add(item)
    }

    fun printName() {
        for (item: Node in list) {
            println("name : ${item.getName()}")
        }
    }
}


```
