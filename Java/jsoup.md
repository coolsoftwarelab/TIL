## JSOUP

>jsoup �� HTML ������ ����� �����͸� ���� �м�, ���� �� �����ϵ��� ����� ���� �ҽ� Java ���̺귯��

## �ȵ���̵忡�� ���
���� ���������� ��ư value �� ���� ���� (Network �۾��̹Ƿ� Async�� �۾� �ʿ�)

**build.gradle**
>implementation 'org.jsoup&#58;jsoup&#58;1.11.3'

** source **
<pre>
<code>doc = Jsoup.connect(http://www.google.com).get();
Element btnK = doc.select("input[name=btnK]").first();
String btnKValue = btnK.attr("value");
Log.d(TAG, "btnKValue : " + btnKValue);		// 'Google �˻�'</code>
</pre>