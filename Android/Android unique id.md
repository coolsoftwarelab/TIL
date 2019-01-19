### Settings.Secure.ANDROID_ID 를 베이스로 UUID 생성

<pre><code>private static String getUUID(Context _context) {
        UUID uuid = null;
        String androidId = Settings.Secure.getString(_context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidId == null || androidId.isEmpty() || androidId.equals("9774d56d682e549c")) {
            uuid = UUID.randomUUID();
        } else {
            try {
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                uuid = UUID.randomUUID();
            }
        }
        return uuid.toString();
}</code></pre>
   
