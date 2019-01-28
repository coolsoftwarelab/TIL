
## Android Sqlite Transaction

Exclusive lock 모드로 동작하여 다른 곳에서 db 접근이 제한되며, 다수의 데이터 처리에 적합
(ex. 단일명령 insert, delete, update 를  반복하는 경우)

간단히 말하면 begin 부터 end 까지 일정 구간을 하나의 트랜잭션으로 처리하여 빠르다.
(100개 정도의 적은 데이터 처리에서도 Transaction 적용 여부에 따라 수십배의 속도 차이가 난다.)

setTransactionSuccessful() 은 commit 의 의미이며, 
이 구문 없이 endTransaction() 을 호출하면 변경내용은 롤백된다.

**가이드**
>db.beginTransaction();
   try {
     ...
	    db.setTransactionSuccessful();
   } finally {
	    db.endTransaction();
   }

**예제 코드**
<pre><code>
try {
		mDB.beginTransaction();
        for (RankItem rankItem : rankItemList) {
            int rank = rankItem.getRank();
            String title = rankItem.getTitle();
            String age = rankItem.getDataAge();

            ContentValues values = new ContentValues();
            values.put(SearchRankEntry.COLUMN_NAME_RANK, rank);
            values.put(SearchRankEntry.COLUMN_NAME_RANK_TITLE, title);
            values.put(SearchRankEntry.COLUMN_NAME_AGE, age);
            mDB.insert(SearchRankEntry.TABLE_NAME, null, values);
        }
        mDB.setTransactionSuccessful();
} catch(Exception e) {
	// error handling
} finally {
	mDB.endTransaction();
}
</pre></code>