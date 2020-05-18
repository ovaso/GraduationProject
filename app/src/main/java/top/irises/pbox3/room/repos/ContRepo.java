package top.irises.pbox3.room.repos;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import top.irises.pbox3.room.dao.ContDao;
import top.irises.pbox3.room.db.PboxDb;
import top.irises.pbox3.room.domain.Cont;

public class ContRepo {
    private LiveData<List<Cont>> allContAlive;
    private ContDao contDao;

    public ContRepo(Context context){
        PboxDb db = PboxDb.getDatabase(context.getApplicationContext());
        contDao = db.getContDao();
        this.allContAlive = contDao.getAllContLive();
    }

    public LiveData<List<Cont>> getFiltered(String pat){return contDao.queryByPattern("%"+pat+"%");}

    public LiveData<List<Cont>> getAllContAlive() {
        return allContAlive;
    }

    public void insertItem(Cont... conts){new InsertAsyncTask(contDao).execute(conts);}

    public void updateItem(Cont... conts){new UpdateAsyncTask(contDao).execute(conts);}

    public void deleteItem(Cont... conts){new DeleteAsyncTask(contDao).execute(conts);}

    public void truncate(){
        new DeleteAllAsyncTask(contDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Cont,Void,Void>{
        private ContDao contDao;
        InsertAsyncTask(ContDao contDao){
            this.contDao = contDao;
        }
        @Override
        protected Void doInBackground(Cont... conts) {
            contDao.insertItem(conts);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Cont, Void, Void> {
        private ContDao contDao;

        UpdateAsyncTask(ContDao contDao){
            this.contDao = contDao;
        }

        @Override
        protected Void doInBackground(Cont... conts) {
            contDao.updateItem(conts);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Cont, Void, Void> {
        ContDao contDao;
        DeleteAsyncTask(ContDao contDao){
            this.contDao = contDao;
        }

        @Override
        protected Void doInBackground(Cont... conts) {
            contDao.deleteItem(conts);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContDao contDao;

        DeleteAllAsyncTask(ContDao contDao) {
            this.contDao = contDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contDao.turncate();
            return null;
        }
    }
}
