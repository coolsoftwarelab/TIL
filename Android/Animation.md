## Android Animation

기존 애니메이션 효과는 gif 방식.  
=>압축률이 낮고, 많은 리소스 필요

2016년 Facebook 에서 Keyframes 라이브러리 제작.

2017년 airbnb 에서 Lottie 라이브러리 제작

https://airbnb.design/lottie/

Keyframes는 적은 용량의 애니메이션을 구현하려는 목적에 맞게 만들다 보니 스펙이 제한적인 반면,  
Lottie는 실제 After Effects에서 사용하는 다양한 효과들을 대부분 지원한다.

### Lottie로 애니메이션 제작 단계

1. Adobe illustrator, sketch 등으로 이미지 생성 -> .ai 파일 또는 svg 추출

2. Adobe After Effects(Bodymovin) 로 이미지 처리 -> json 파일 추출

### Android

애니메이션 파일(.json) 은 assets 에 위치

build.gradle
```
implementation 'com.airbnb.android:lottie:' + lottieVersion

/*
Project build.gradle
ext {
    lottieVersion = '3.3.1'
}
*/
```

xml
```
<com.airbnb.lottie.LottieAnimationView
    android:id="@+id/lav_thumbUp"
    android:layout_width="100dp"
    android:layout_height="100dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:lottie_autoPlay="true"
    app:lottie_fileName="thumbup.json"
    app:lottie_loop="true"
    app:lottie_speed="1.0" />
```

