## Room Persistence Library

> Room 은 SQLite의 추상 레이어를 제공하여 SQLite의 모든 기능을 활용할 수 있다.

### 구성요소
1. Datbase<br>
> database의 holder를 만든다. annotation으로 entities(Table의 구조와 mapping되는 class)를 정의하고, 클래스 내부에 dao를 정의한다.

```
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

인스턴스 생성
```
AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();
```

2. Entity<br>
> 데이터베이스 내의 테이블을 나타낸다.
database의 row와 mapping되는 class, 즉 table의 구조를 나타내는데  Database에서 entities함수를 통해 접근할 수 있다.

```
@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}
```

3. DAO<br>
> 데이터베이스에 액세스하는 데 사용되는 메소드를 포함한다.
database를 접근하는 함수들이 정의되는 class or interface.
@Database로 정의된 class는 내부에 인자가 없고 @Dao annotation이 되어있는 class를 return하는 abstract 함수를 포함하고 있다.

```
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
```

![](https://developer.android.com/images/training/data-storage/room_architecture.png)<br>
그림. 룸 아키텍처 다이어그램

### CRUD
**각 작업은 메인 스레드에서 실행하지 말 것**
