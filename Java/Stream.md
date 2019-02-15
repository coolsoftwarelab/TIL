>순차 및 병렬 집계 연산을 지원하는 요소의 시퀀스입니다. 다음 예제에서는 Stream 및 IntStream을 사용하는 집계 연산을 보여준다.

```
     int sum = widgets.stream()
                      .filter(w -> w.getColor() == RED)
                      .mapToInt(w -> w.getWeight())
                      .sum();
```

>스트림 파이프 라인은 순차적 또는 병렬로 실행될 수 있다. 
순차적 또는 병렬 스트림은 실행의 초기 선택
Ex) 순차적 스트림은 Collection.stream() 으로 생성, 병렬 스트림은 Collection.parallelStream() 으로 생성
