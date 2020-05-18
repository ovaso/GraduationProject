package top.irises.pbox3.vms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import top.irises.pbox3.room.domain.Assign;
import top.irises.pbox3.room.repos.AssignRepo;

public class AddPageViewModel extends AndroidViewModel {

    private MutableLiveData<String> startTime;
    private MutableLiveData<String> endTime;
    private AssignRepo repo;

    public AddPageViewModel(@NonNull Application application) {
        super(application);
        repo = new AssignRepo(application);
    }

    public void addAssign(Assign... assigns){
        repo.insertItem(assigns);
    }

    public void setStartTime(MutableLiveData<String> startTime) {
        if (startTime == null) {
            startTime = new MutableLiveData<String>();
        }
        this.startTime = startTime;
    }

    public void setEndTime(MutableLiveData<String> endTime) {
        if (endTime == null) {
            endTime = new MutableLiveData<String>();
        }
        this.endTime = endTime;
    }

    public MutableLiveData<String> getStartTime() {
        if (startTime == null) {
            startTime = new MutableLiveData<String>();
        }
        return startTime;
    }

    public MutableLiveData<String> getEndTime() {
        if (endTime == null) {
            endTime = new MutableLiveData<String>();
        }
        return endTime;
    }
}
