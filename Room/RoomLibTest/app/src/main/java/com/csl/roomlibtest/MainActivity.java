package com.csl.roomlibtest;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    final String DB_NAME = "MY-DB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, DB_NAME).allowMainThreadQueries().build();

        // Creaete
        UserDao dao = db.userDao();
        User user = new User();
        user.uid = 1;
        user.firstName = "gil dong";
        user.lastName = "hong";
        dao.insertAll(user);

        // Read
        User savedUser = db.userDao().getAll().get(0);
        Log.d("JDEBUG", "lastName : " + savedUser.lastName + " / firstName : " + savedUser.firstName);

        // Update
        savedUser.lastName = "kim";
        dao.updateUsers(savedUser);
        User tmpUser = db.userDao().getAll().get(0);
        Log.d("JDEBUG", "lastName : " + savedUser.lastName + " / firstName : " + savedUser.firstName);

        // Delete
        dao.delete(savedUser);
        Log.d("JDEBUG", "User count after delete : " + db.userDao().getAll().size());
    }
}
