수정중
### 안드로이드에서 메모리 릭 방지 처리

```
@Override
protected void onDestroy() {
	if(disposableObserver != null && !disposableObserver.isDisposed()) {
	    disposableObserver.dispose();
	}
	super.onDestroy();
}
```
