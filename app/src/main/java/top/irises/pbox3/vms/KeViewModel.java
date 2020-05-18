package top.irises.pbox3.vms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import top.irises.pbox3.room.domain.Ke;
import top.irises.pbox3.room.repos.KeRepo;

public class KeViewModel extends AndroidViewModel {

    private KeRepo repo;
    private MutableLiveData<Long> expireTime;
    private MutableLiveData<Boolean> pk;


    public KeViewModel(@NonNull Application application) {
        super(application);
        repo = new KeRepo(application);
    }

    public LiveData<List<Ke>> getAllKeAlive(){
        return repo.getAllKeAlive();
    }
    public LiveData<List<Ke>> getFilteredAlived(String pat){return repo.getFiltered(pat);}

    public MutableLiveData<Boolean> getPk() {
        if (pk == null) {
            pk = new MutableLiveData<Boolean>();
        }
        return pk;
    }

    public void setK(MutableLiveData<Boolean> pk) {
        this.pk = pk;
    }

    public MutableLiveData<Long> getExpireTime() {
        if (expireTime == null) {
            expireTime = new MutableLiveData<>();
        }
        return expireTime;
    }

    public void setExpireTime(MutableLiveData<Long> expireTime) {
        this.expireTime = expireTime;
    }

    public void insertItem(Ke... kes){repo.insertItem(kes);}

    public void deleteItem(Ke... kes){repo.deleteItem(kes);}

    public void truncate(){
        repo.truncate();
    }

}
