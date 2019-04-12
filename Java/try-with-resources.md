## resource 자동 해제 기능

자원 해제 (close) 를 명시적으로 호출 할 필요가 없다.
#### try-with-resources
```
static String readFirstLineFromFile(String path) throws IOException {
    try (BufferedReader br =
                   new BufferedReader(new FileReader(path))) {
        return br.readLine();
    }
}
```

위 `BufferedReader br = new BufferedReader(new FileReader(path))` 가 resource 이다.

#### 기존 finally block
```
static String readFirstLineFromFileWithFinallyBlock(String path)
                                                     throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine();
    } finally {
        if (br != null) br.close();
    }
}
```
