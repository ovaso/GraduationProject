package top.irises.pbox3.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import top.irises.pbox3.room.domain.Pay;

@Dao
public interface PayDao {

    @Insert
    void insertItem(Pay... pays);

    @Update
    void updateItem(Pay... pays);

    @Delete
    void deleteItem(Pay... pays);

    @Query("DELETE FROM PAY")
    void truncate();

    @Query("SELECT * FROM PAY ORDER BY createTime DESC")
    LiveData<List<Pay>> getAllPayLive();
}
