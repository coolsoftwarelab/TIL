## 함수형 프로그래밍 (Functional programming)

>함수형 프로그래밍은 자료 처리를 수학적 함수의 계산으로 취급하고 상태와 가변 데이터를 멀리하는 프로그래밍 패러다임의 하나이다. 명령형 프로그래밍에서는 상태를 바꾸는 것을 강조하는 것과는 달리, 함수형 프로그래밍은 함수의 응용을 강조한다.

<br>

>함수형 프로그래밍은 순수 함수를 작성하는 것, 그러니까 숨겨진 입력이나 출력을 최대한 제거하여 가능한한 우리 코드의 대부분이 단지 입력과 출력의 관계를 기술하게끔 하는 것을 말한다.

### 순수함수란?
>모든 입력이 입력으로 선언되고 (숨겨진 것이 없어야 한다) 마찬가지로 모든 출력이 출력으로 선언된 함수를 ‘순수(pure)’하다고 부른다.

입력과 출력이 명확하지 않으면 부작용 (Side effect) 이 발생할 수 있다. 이것을 최소화 하고자 하는게 함수형 프로그래밍이다.

아래는 단순한 입력과 출력을 보여준다.
```
public int square(int x) {
    return x * x;
}
```

아래는 입력과 출력의 두번째 형태이다.
```
public void processNext() {
    Message message = InboxQueue.popMessage();

    if (message != null) {
        process(message);
    }
}
```
위 함수는 입력이 없고 어떤 값도 반환하지 않지만 무언가 의존성을 가지며 어떤 동작을 한다. 숨겨진 입출력을 가진다는 의미이다. 
숨겨진 입력은 popMessage()를 호출하기 전의 InboxQueue 상태이고, 숨겨진 출력은 process 호출로 인해 발생하는 모든 것과 모든 일이 끝나고 났을 때의 InboxQueue 상태이다.

아래 함수는 숨겨진  입력을 가진다.
```
public Program getCurrentProgram(TVGuide guide, int channel) {
  Schedule schedule = guide.getSchedule(channel);

  Program current = schedule.programAt(new Date());

  return current;
}
```

(new Date()) 부분이 숨겨진 입력이다. 이 숨겨진 입력을 아래와 같이 명시적인 입력으로 바꿈으로서 부작용을 줄일 수 있다.

```
public Program getProgramAt(TVGuide guide, int channel, Date when) {
  Schedule schedule = guide.getSchedule(channel);

  Program program = schedule.programAt(when);

  return program;
}
```
