>순차 및 병렬 집계 연산을 지원하는 요소의 시퀀스입니다. 다음 예제에서는 Stream 및 IntStream을 사용하는 집계 연산을 보여줍니다.

```
     int sum = widgets.stream()
                      .filter(w -> w.getColor() == RED)
                      .mapToInt(w -> w.getWeight())
                      .sum();
```
