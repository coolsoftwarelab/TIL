## ���ٽ� (Lambda Expressions)
> �ĺ��� ���� ���� ������ �Լ� ǥ����

** ��� **
(x, y)->{return x+y;}

** ����. �Ϲ����� ��ư Ŭ�� �̺�Ʈ **
<pre><code>mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JDEBUG", "Click!");
            }
});</code></pre>

** ����. ���ٽ� **

<pre><code>mTestButton.setOnClickListener(v -> Log.d("JDEBUG", "Click!"));</code></pre>

�ȵ���̵� ��Ʃ������� ��� �� build.gradle �� �Ʒ� ���� �߰�
<pre><code>android {
...
		compileOptions {
	       	sourceCompatibility JavaVersion.VERSION_1_8
	       	targetCompatibility JavaVersion.VERSION_1_8
	    }
...
}</code></pre>