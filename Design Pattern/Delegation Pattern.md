## Delegation Pattern (위임 패턴)

어떤 객체의 조작 일부를 다른 객체에게 넘김
위탁자(delegator) → 수탁자(delegate)

다른 클래스의 기능을 사용하되 그 기능을 변경하지 않으려면 상속 대신 위임

```
public class Main {
    public static void main(String[] args) { 
        Printer printer = new Printer();
        printer.print(); // 프린터야, 프린트해줘.
    }
}

// "delegator" 위탁자 (떠넘기는 사람, 갑)
class Printer { 
    RealPrinter p = new RealPrinter(); // 대신해줄 객체 생성
    void print() { p.print(); } // 위임!
}

// "delegate" 수탁자 (위임 받은 사람, 실무수행자, 을)
class RealPrinter { 
    void print() { System.out.println("something"); }
}
```


참조 : https://zetawiki.com/wiki/%EC%9C%84%EC%9E%84_%ED%8C%A8%ED%84%B4
