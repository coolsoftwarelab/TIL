## Builder Pattern
>생성자(constructor) 인자가 많거나 특정 인자값들만 설정해서 인스턴스를 생성하고 싶을 때 효과적이다.  
(빌더 패턴이란 복합 객체의 생성 과정과 표현 방법을 분리하여 동일한 생성 절차에서 서로 다른 표현 결과를 만들 수 있게 하는 패턴이다)


```
class Pizza(builder: PizzaBuilder) {
    private var dough = builder.dough
    private var sauce = builder.sauce
    private var topping = builder.topping
    private var etc = builder.etc

    class PizzaBuilder(
        var dough: String,  // required parameter
        var sauce: String   // required parameter
    ) {
        var topping = "";   // optional
        var etc = "";       // optional

        fun topping(topping: String): PizzaBuilder {
            this.topping = topping
            return this
        }

        fun etc(etc: String): PizzaBuilder {
            this.etc = etc
            return this
        }

        fun build(): Pizza {
            return Pizza(this)
        }
    }
}

// Main
val pizza = Pizza.PizzaBuilder("thin", "hot")
    .topping("apple")
    //.etc("ASAP")    // optional
    .build()
```
