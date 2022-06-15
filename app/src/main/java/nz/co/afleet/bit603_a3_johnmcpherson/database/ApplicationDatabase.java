package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// default visibility so that only authorised classes can use it
@Database(entities = {User.class}, version = 1)
abstract class ApplicationDatabase extends RoomDatabase {
    private static ApplicationDatabase instance;

    static ApplicationDatabase getInstance(Context context) {
        if (instance == null) { // initialise if not already initialised
            instance = Room.databaseBuilder(context, ApplicationDatabase.class, "inventorydb").allowMainThreadQueries().build();
        }
        return instance;
    }


    // !!!!! TESTING SUPPORT ONLY !!!!!
    static void TESTING_tearDown() {
        if (instance != null) {
            if (instance.isOpen()) {
                instance.close();
                instance = null;
            }
        }
    }

    public abstract DaoUser daoUser();

    public abstract DaoInventory daoInventory();
}
