### Android Flavor


<pre><code>flavorDimensions 'mode'
    productFlavors {
        demo {
            dimension 'mode'
            buildConfigField("boolean", "IS_DEMO", "true")
        }
        full {
            dimension 'mode'
            buildConfigField("boolean", "IS_DEMO", "false")
        }
    }
</code></pre>

<pre><code>boolean isDemo = BuildConfig.IS_DEMO;</code></pre>
