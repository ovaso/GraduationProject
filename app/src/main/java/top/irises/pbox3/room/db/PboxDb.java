package top.irises.pbox3.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import top.irises.pbox3.room.dao.AssignDao;
import top.irises.pbox3.room.dao.ContDao;
import top.irises.pbox3.room.dao.KeDao;
import top.irises.pbox3.room.dao.PayDao;
import top.irises.pbox3.room.domain.Assign;
import top.irises.pbox3.room.domain.Cont;
import top.irises.pbox3.room.domain.Ke;
import top.irises.pbox3.room.domain.Pay;

//设置为单例

@Database(entities = {
        Assign.class,
        Cont.class,
        Ke.class,
        Pay.class
        },version = 1,exportSchema = false)
public abstract class PboxDb extends RoomDatabase {
    private static PboxDb INSTANCE;
    public static synchronized PboxDb getDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),PboxDb.class,"pbox.db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract AssignDao getAssignDao();
    public abstract ContDao getContDao();
    public abstract KeDao getKeDao();
    public abstract PayDao getPayDao();
}
