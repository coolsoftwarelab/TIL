## 스트림이란?

**Stream 은 집계 연산을 지원하는 소스의 객체 시퀀스이다.**

다음 예제에서는 Stream 및 IntStream을 사용하는 집계 연산을 보여준다.

```
     int sum = widgets.stream()
                      .filter(w -> w.getColor() == RED)
                      .mapToInt(w -> w.getWeight())
                      .sum();
```

>스트림 파이프 라인은 순차적 또는 병렬로 실행될 수 있다.<br>
순차적 또는 병렬 스트림은 실행의 초기 선택
Ex) 순차적 스트림은 Collection.stream() 으로 생성, 병렬 스트림은 Collection.parallelStream() 으로 생성

<br>

- 요소의 순서 : 스트림은 순차적인 방식으로 특정 유형의 요소 집합을 제공한다. 스트림은 필요에 따라 요소를 가져 오거나 계산하고 요소를 저장하지 않는다.

- 소스 : 스트림은 컬렉션, 배열 또는 I / O 리소스를 입력 소스로 사용

- 집계 연산 : 스트림은 필터, 매핑, 제한, 축소, 찾기, 일치 등과 같은 집계 연산을 지원한다.

- 파이프 라이닝 - 대부분의 스트림 작업은 스트림 자체를 리턴하므로 결과가 파이프 라인 될 수 있다. 이러한 작업을 중간 작업이라고하며 그 기능은 입력을 처리하고 다음 출력을 대상으로 반환하는 것이다. collect () 메소드는 스트림의 끝을 표시하기 위해 파이프 라이닝 작업의 끝에 존재하는 터미널 작업이다.

- 자동 반복 : 명시 적 반복이 필요한 컬렉션과는 달리 스트림 오퍼레이션은 제공된 소스 요소에 대해 내부적으로 반복을 수행


#### Map
>스트림에있는 값을 특정 방식으로 변환하고 새로운 스트림 반환

```
        List<String> alpha = Arrays.asList("a", "b", "c", "d");

        //Before Java8
        List<String> alphaUpper = new ArrayList<>();
        for (String s : alpha) {
            alphaUpper.add(s.toUpperCase());
        }

        System.out.println(alpha); //[a, b, c, d]
        System.out.println(alphaUpper); //[A, B, C, D]

        // Java 8
        List<String> collect = alpha.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(collect); //[A, B, C, D]
```
