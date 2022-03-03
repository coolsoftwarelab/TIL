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

![](https://developer.apple.com/library/archive/documentation/NetworkingInternetWeb/Conceptual/CoreBluetooth_concepts/Art/CBDevices1_2x.png)

#### Slave 와 Master의 각 과정 별 역할

- Connection 전의 역할(Advertiser, Scanner), 후의 역할(Master, Slave)로 분류된다.

![](https://enidanny.github.io/assets/images/ble-connection.png)

### 전체 연결과정

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FDIwoJ%2FbtqUpaNXJpz%2F8FqxITJKGKEAWxXDRmuyL0%2Fimg.png)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb8erTu%2FbtqUnK9PoZ4%2FodCpd0DvA5fNwRAhNRkt90%2Fimg.png)

### BLE 계층구조

![](https://punchthrough.com/wp-content/uploads/2017/11/throughput-part-2-graphic-01.png)

### GATT

>GATT 서버 vs GATT 클라이언트. 두 기기 사이에 연결이 설정되었을 때 서로 통신하는 방법을 결정  
현재 모든 저전력 애플리케이션 프로필은 GATT에 기초합니다.

- GATT server 는 자신이 제공하는 서비스와 데이터를 아래와 같은 계층구조로 만들어서보관

![](https://jung-max.github.io/images/Android-Bluetooth-low-energy/01.png)

- Profile : BLE 장치가 어떤 일을 하는 장치인지 나타내는 개념적 구분(심박 profile, 혈압 profile 등)
- Service : 특정 기능과 과련이 있는 데이터 집합. 서비스끼리 구분하기 위해 각각 UUID 값을 가진다
- Chracteristic : 
