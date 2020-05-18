package top.irises.pbox3.vms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import top.irises.pbox3.room.domain.Ke;
import top.irises.pbox3.room.repos.KeRepo;

public class AddKeyViewModel extends AndroidViewModel {

    private MutableLiveData<String> expiredTime;
    private KeRepo repo;

    public AddKeyViewModel(@NonNull Application application) {
        super(application);
        repo = new KeRepo(application);
    }

    public MutableLiveData<String> getExpiredTime() {
        if (expiredTime == null) {
            expiredTime = new MutableLiveData<String>();
        }
        return expiredTime;
    }

    public void setExpiredTime(MutableLiveData<String> expiredTime) {
        this.expiredTime = expiredTime;
    }

    public void insertItem(Ke... kes){
        repo.insertItem(kes);
    }
}
