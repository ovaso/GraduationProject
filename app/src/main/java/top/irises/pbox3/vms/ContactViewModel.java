package top.irises.pbox3.vms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import top.irises.pbox3.room.domain.Cont;
import top.irises.pbox3.room.repos.ContRepo;

public class ContactViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private ContRepo repo;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repo = new ContRepo(application);
    }

    public LiveData<List<Cont>> getFilteredAlived(String pat){return repo.getFiltered(pat);}

    public LiveData<List<Cont>> getAllContAlive(){return repo.getAllContAlive();}

    public void updateItem(Cont... conts){repo.updateItem(conts);}

    public void deleteItem(Cont... conts){repo.deleteItem(conts);}

    public void truncate(){
        repo.truncate();
    }

}
