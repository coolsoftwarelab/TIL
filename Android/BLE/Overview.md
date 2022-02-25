참조 : https://developer.android.com/guide/topics/connectivity/bluetooth/connect-gatt-server?hl=en

## BLE (Bleutooth Low Energy)

- 주변 장치 간에 소량의 데이터 전송
- 근접 센서와 상호작용

### 주요용어 및 개념

- Generic Attribute Profile (GATT)
  - GATT 프로필은 BLE 링크를 통해 "속성"으로 알려진 짧은 데이터 조각을 보내고 받기 위한 일반 사양. 현재의 모든 BLE 애플리케이션 프로필은 GATT를 기반으로 한다

- Profiles
  - 프로필은 특정 응용 프로그램에서 장치가 작동하는 방식에 대한 사양입니다. 장치는 둘 이상의 프로필을 구현할 수 있습니다. ex)심박수 모니터, 배터리 잔량 감지기 등
- Attribute Protocol (ATT)
  - GATT는 속성 프로토콜(ATT) 위에 구축. 이를 GATT/ATT라고도 한다. ATT는 BLE 장치에서 실행하도록 최적화되어 있다. 
     가능한 한 적은 바이트를 사용하고, 각 속성은 UUID(Universally Unique Identifier)로 고유하게 식별. ATT에 의해 전송되는 속성 은 특성 및 서비스 로 형식이 지정된다.
- Characteristic
  - 클래스와 유사항 유형. 특성에는 단일 값과 특성 값을 설명하는 O-n 설명자가 포함된다
- Descriptor
  - 설명자는 특성 값을 설명하는 정의된 속성
- Service
  - 서비스는 특성의 모음. ex)심박수 측정"과 같은 특성을 포함하는 "심박수 모니터"라는 서비스

### 역할과 책임

>장치가 BLE 장치와 상호 작용할 때 다음 역할 및 책임이 적용된다.

- GATT 서버 대 GATT 클라이언트.  
  이것은 두 장치가 연결을 설정한 후 서로 통신하는 방법을 결정합니다.  
  차이점을 이해하기 위해 Android 전화와 BLE 장치인 활동 추적기가 있다고 가정.
  - BLE 연결을 설정하려면 각각 하나가 필요
  - 전화는 중심 역할을 지원합니다, 활동 추적기는 주변 장치 역할을 지원  
  - **주변 장치만 지원하는 두 가지는 서로 통신할 수 없고 중앙만 지원하는 두 가지는 서로 통신할 수 없습니다.**

## BLE 장치찾기

`startScan()`으로 찾고 `ScanCallback` 을 이용해 결과를 콜백에서 처리

```kotlin
private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
private var scanning = false
private val handler = Handler()

// Stops scanning after 10 seconds.
private val SCAN_PERIOD: Long = 10000

private fun scanLeDevice() {
    if (!scanning) { // Stops scanning after a pre-defined scan period.
        handler.postDelayed({
            scanning = false
            bluetoothLeScanner.stopScan(leScanCallback)
        }, SCAN_PERIOD)
        scanning = true
        bluetoothLeScanner.startScan(leScanCallback)
    } else {
        scanning = false
        bluetoothLeScanner.stopScan(leScanCallback)
    }
}


private val leDeviceListAdapter = LeDeviceListAdapter()
// Device scan callback.
private val leScanCallback: ScanCallback = object : ScanCallback() {
    override fun onScanResult(callbackType: Int, result: ScanResult) {
        super.onScanResult(callbackType, result)
        leDeviceListAdapter.addDevice(result.device)
        leDeviceListAdapter.notifyDataSetChanged()
    }
}
```


참고 : Bluetooth LE 장치만 검색 하거나 클래식 Bluetooth 장치만 검색할 수 있습니다.

**Bluetooth LE와 클래식 장치를 동시에 스캔할 수는 없습니다.**

## GATT 서버에 연결
- BLE 장치에서 GATT 서버에 연결하려면 `connectGatt()` 사용. 

```kotlin
var bluetoothGatt: BluetoothGatt? = null
...

bluetoothGatt = device.connectGatt(this, false, gattCallback)
```
BLE 장치가 호스팅하는 GATT 서버에 연결하고 `BluetoothGatt` 인스턴스를 반환하며, GATT 클라이언트 작업을 수행하는 데 사용할 수 있습니다.  
호출자는 GATT 클라이언트입니다.  
`BluetoothGattCallback` 연결 상태 및 추가 GATT 클라이언트 작업과 같은 결과를 클라이언트에 전달하는 데 사용됩니다.

## 바인딩된 서비스 설정

다음 예에서 BLE 앱은 Bluetooth 장치에 연결하고, 장치 데이터를 표시하고, 장치에서 지원하는 GATT 서비스 및 특성을 표시 하는 활동을 제공합니다.  
사용자 입력에 따라 이 활동 은 BLE API를 통해 BLE 장치와 상호 작용 하는 Service호출 된 과 통신합니다.  
BluetoothLeService통신은 활동이 장치에 연결하고 기능을 호출 할 수 있도록 하는 바인딩된 서비스 를 사용하여 수행됩니다. 

```kotlin
class BluetoothLeService : Service() {

    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService() : BluetoothLeService {
            return this@BluetoothLeService
        }
    }
}
```

Activity는 서비스 시작 bindService()을 위한 전달, 연결 및 연결 해제 이벤트를 수신 Intent 구현,  
추가 연결 옵션을 지정하는 플래그를 사용하여 서비스를 시작할 수 있습니다. `ServiceConnection`

```kotlin
class DeviceControlActivity : AppCompatActivity() {

    private var bluetoothService : BluetoothLeService? = null

    // Code to manage Service lifecycle.
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            componentName: ComponentName,
            service: IBinder
        ) {
            bluetoothService = (service as LocalBinder).getService()
            bluetoothService?.let { bluetooth ->
                // call functions on service to check connection and connect to devices
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            bluetoothService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gatt_services_characteristics)

        val gattServiceIntent = Intent(this, BluetoothLeService::class.java)
        bindService(gattServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }
}
```

### BluetoothAdapter 설정

서비스가 








