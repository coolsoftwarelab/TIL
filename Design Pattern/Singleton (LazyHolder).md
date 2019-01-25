
>Initialization-on-demand holder idiom

**Thread-safe 하며 불필요하게 메모리를 미리 점유하지 않아서 효율적**

<pre><code>public class Something {
    private Something() {}

    private static class LazyHolder {
        static final Something INSTANCE = new Something();
    }

    public static Something getInstance() {
        return LazyHolder.INSTANCE;
    }
}
</code></pre>
