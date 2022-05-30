package nz.co.afleet.bit603_a3_johnmcpherson.database;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// interface and methods have default visibility to restrict access to authorised classes
@androidx.room.Dao
public interface DaoUser {
    @Insert
    void addUser(User user);

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query ("DELETE FROM User WHERE User.name = :userName")
    abstract void deleteByName(String userName);
}
