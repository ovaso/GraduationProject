package top.irises.pbox3.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import top.irises.pbox3.room.domain.Cont;


@Dao
public interface ContDao {

    @Insert
    void insertItem(Cont... conts);

    @Update
    void updateItem(Cont... conts);

    @Delete
    void deleteItem(Cont... conts);

    @Query("DELETE FROM CONT")
    void turncate();

    @Query("SELECT * FROM CONT ORDER BY name ASC")
    LiveData<List<Cont>> getAllContLive();

    @Query("SELECT * FROM CONT WHERE name LIKE :pat ORDER BY name ASC")
    LiveData<List<Cont>> queryByPattern(String pat);
}
