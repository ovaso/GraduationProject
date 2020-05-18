package top.irises.pbox3.room.repos;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;

import java.util.List;

import top.irises.pbox3.room.dao.KeDao;
import top.irises.pbox3.room.db.PboxDb;
import top.irises.pbox3.room.domain.Ke;

public class KeRepo {
    private LiveData<List<Ke>> allKeAlive;
    private KeDao keDao;

    public KeRepo(Context context){
        PboxDb db = PboxDb.getDatabase(context.getApplicationContext());
        keDao = db.getKeDao();
        this.allKeAlive = keDao.getAllKeLive();
    }

    public LiveData<List<Ke>> getAllKeAlive() {
        return allKeAlive;
    }
    public LiveData<List<Ke>> getFiltered(String pat){
        return keDao.queryByPattern("%"+pat+"%");
    }

    public void insertItem(Ke... kes){
        new InsertAsyncTask(keDao).execute(kes);
    }
    public void deleteItem(Ke... kes){
        new DeleteAsyncTask(keDao).execute(kes);
    }
    public void truncate(){
        new DeleteAllAsyncTask(keDao);
    }

    static class InsertAsyncTask extends AsyncTask<Ke,Void,Void>{
        private KeDao keDao;
        InsertAsyncTask(KeDao keDao){
            this.keDao = keDao;
        }
        @Override
        protected Void doInBackground(Ke... kes) {
            keDao.insertItem(kes);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Ke, Void, Void> {
        private KeDao keDao;

        UpdateAsyncTask(KeDao keDao){
            this.keDao = keDao;
        }

        @Override
        protected Void doInBackground(Ke... kes) {
            keDao.updateItem(kes);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Ke, Void, Void> {
        KeDao keDao;
        DeleteAsyncTask(KeDao keDao){
            this.keDao = keDao;
        }

        @Override
        protected Void doInBackground(Ke... kes) {
            keDao.deleteItem(kes);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private KeDao keDao;

        DeleteAllAsyncTask(KeDao keDao) {
            this.keDao = keDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            keDao.turncate();
            return null;
        }
    }
}
