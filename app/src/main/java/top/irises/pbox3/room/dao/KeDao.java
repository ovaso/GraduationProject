package top.irises.pbox3.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import top.irises.pbox3.room.domain.Ke;

@Dao
public interface KeDao {

    @Insert
    void insertItem(Ke... kes);

    @Update
    void updateItem(Ke... kes);

    @Delete
    void deleteItem(Ke... kes);

    @Query("DELETE FROM KE")
    void turncate();

    @Query("SELECT * FROM KE ORDER BY createTime DESC")
    LiveData<List<Ke>> getAllKeLive();

    @Query("SELECT * FROM KE WHERE ps LIKE :pat ORDER BY createTime DESC")
    LiveData<List<Ke>> queryByPattern(String pat);
}
