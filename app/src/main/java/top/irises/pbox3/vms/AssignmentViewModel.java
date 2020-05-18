package top.irises.pbox3.vms;

import android.app.Application;
import android.text.style.TtsSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import top.irises.pbox3.room.domain.Assign;
import top.irises.pbox3.room.repos.AssignRepo;

public class AssignmentViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private AssignRepo repo;
    private MutableLiveData<Boolean> order;

    public AssignmentViewModel(@NonNull Application application) {
        super(application);
        repo = new AssignRepo(application);
    }

    public MutableLiveData<Boolean> getOrder() {
        if (order == null) {
            order = new MutableLiveData<Boolean>();
        }
        return order;
    }

    public void setOrder(MutableLiveData<Boolean> order) {
        this.order = order;
    }

    public LiveData<List<Assign>> getAllAssignLive(){
        return repo.getAllAssignAlive();
    }

    public void insertItem(Assign... assigns){
        repo.insertItem(assigns);
    }

    public void updateItem(Assign... assigns){
        repo.updateItem(assigns);
    }

    public void deleteItem(Assign... assigns){
        repo.deleteItem(assigns);
    }

    public void truncate(){
        repo.truncate();
    }
}
