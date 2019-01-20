### 투명한 배경의 흰색 아이콘으로 만들어야 한다. (White icon)

안드로이드 Pie (9.0 / API 28) 기기에서 일반 이미지 아이콘을 Notification smallIcon 으로 사용하면

원래 이미지가 아니라 흰색 또는 회색 단색으로 푸시 아이콘이 표시된다.



투명한 배경의 흰색 아이콘을 만든 후 코드 상에서 분기하면 해결.


<pre><code>if (android.os.Build.VERSION.SDK_INT >= 28) {   // Android P
    icon = R.drawable.ic_white_noti;
}</pre></code>


참고 제작도구 : Android studio > New > Image Assets > IconType(Notification)
