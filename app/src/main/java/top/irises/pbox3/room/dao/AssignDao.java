package top.irises.pbox3.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import top.irises.pbox3.room.domain.Assign;

@Dao
public interface AssignDao {

    @Insert
    void insertItem(Assign... assigns);

    @Update
    void updateItem(Assign... assigns);

    @Delete
    void deleteItem(Assign... assigns);

    @Query("DELETE FROM ASSIGN")
    void turncate();

    @Query("SELECT * FROM ASSIGN ORDER BY createTime DESC")
    LiveData<List<Assign>> getAllAssignLive();

}
