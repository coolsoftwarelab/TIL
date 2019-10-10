```
private static void insertionSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        int insertionValue = arr[i + 1];
        for (int k = i; k > 0; k--) {
            if (arr[k] > arr[k + 1]) {
                int tmp = arr[k];
                arr[k] = arr[k + 1];
                arr[k + 1] = tmp;
            }
        }
    }
}
```
