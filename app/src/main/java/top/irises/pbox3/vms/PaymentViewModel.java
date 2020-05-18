package top.irises.pbox3.vms;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import top.irises.pbox3.room.domain.Pay;
import top.irises.pbox3.room.repos.PayRepo;

public class PaymentViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    PayRepo repo;
    Context context;

    public PaymentViewModel(@NonNull Application application) {
        super(application);
        repo = new PayRepo(application);
    }

    //设定 界面值
    public LiveData<List<Pay>> getAllPayAlive(){ return repo.getAllPayAlive(); }
    public void insertItem(Pay... pays){
        repo.insertItem(pays);
    }
    public void truncate(){
        Log.d("TRUNCATE","BEFORE");
        repo.truncate();
        Log.d("TRUNCATE","AFTER");
    }
    public void deleteItem(Pay... pays){
        repo.deleteItem(pays);
    }
}
