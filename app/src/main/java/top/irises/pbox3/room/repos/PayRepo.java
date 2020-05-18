package top.irises.pbox3.room.repos;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import top.irises.pbox3.room.dao.PayDao;
import top.irises.pbox3.room.db.PboxDb;
import top.irises.pbox3.room.domain.Pay;

public class PayRepo {
    private LiveData<List<Pay>> allPayAlive;
    private PayDao payDao;

    public PayRepo(Context context){
        PboxDb db = PboxDb.getDatabase(context.getApplicationContext());
        payDao = db.getPayDao();
        this.allPayAlive = payDao.getAllPayLive();
    }

    public LiveData<List<Pay>> getAllPayAlive() {
        return allPayAlive;
    }

    public void insertItem(Pay... pays){
        new InsertAsyncTask(payDao).execute(pays);
    }

    public void deleteItem(Pay... pays){
        new DeleteAsyncTask(payDao).execute(pays);
    }

    public void truncate(){
        new DeleteAllAsyncTask(payDao).execute();
    }


    static class InsertAsyncTask extends AsyncTask<Pay,Void,Void>{
        private PayDao payDao;
        InsertAsyncTask(PayDao payDao){
            this.payDao = payDao;
        }
        @Override
        protected Void doInBackground(Pay... pays) {
            payDao.insertItem(pays);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Pay, Void, Void> {
        private PayDao payDao;

        UpdateAsyncTask(PayDao payDao){
            this.payDao = payDao;
        }

        @Override
        protected Void doInBackground(Pay... pays) {
            payDao.updateItem(pays);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Pay, Void, Void> {
        PayDao payDao;
        DeleteAsyncTask(PayDao payDao){
            this.payDao = payDao;
        }

        @Override
        protected Void doInBackground(Pay... pays) {
            payDao.deleteItem(pays);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private PayDao payDao;

        DeleteAllAsyncTask(PayDao payDao) {
            this.payDao = payDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            payDao.truncate();
            return null;
        }
    }

}
