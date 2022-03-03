### 연결과정 요약
1. Peripheral(Slave)이 주변에 시그널을 뿌린다(advertising)
2. Central(Master)이 주변에 디바이스가 있는지 스캐닝을 한다
3. 두 디바이스가 연결한다

![](https://media.vlpt.us/images/zhemdrawer/post/337793ec-7632-4424-bc8a-f74ed643a0d7/bluetooth-ble-roles.png)

### Advertise / Connection

#### Advertise Mode
>Advertiser가 자신의 존재를 알리거나 작은 데이터를 보낼 목적으로 주변 모든 디바이스에게 신호를 보낸다

- Advertiser : 신호를 주기적으로 보내는 디바이스 (ex. 심박센서)
- Observer : Advertiser가 보내는 신호를 받기위해 주기적으로 스캐닝하는 디바이스 (ex. 스마트폰)

![](https://yonghyunlee.gitlab.io/assets/img/BLE.png)

#### Connection Mode
>양방향으로 데이터를 주고 받거나 많은 양의 데이터를 주고 받을 때 사용. 1:1 통신이다

- Central(Master) : 다른 디바이스의 신호를 스캐닝하다가 연결을 요청하는 기기
- Peripheral(Slave) : 주기적으로 신호를 보내다가 Central 디바이스가 연결 요청을 보내면 이를 수락하고 연결한다

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcfjdGh%2FbtqUmJ4pvBg%2FvZIrv1sQQXKoDutPOt4880%2Fimg.png)

![](https://developer.apple.com/library/archive/documentation/NetworkingInternetWeb/Conceptual/CoreBluetooth_concepts/Art/CBDevices1_2x.png)

#### Slave 와 Master의 각 과정 별 역할

![](https://enidanny.github.io/assets/images/ble-connection.png)

### 전체 연결과정

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FDIwoJ%2FbtqUpaNXJpz%2F8FqxITJKGKEAWxXDRmuyL0%2Fimg.png)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb8erTu%2FbtqUnK9PoZ4%2FodCpd0DvA5fNwRAhNRkt90%2Fimg.png)

