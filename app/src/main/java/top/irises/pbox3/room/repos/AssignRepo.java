package top.irises.pbox3.room.repos;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import top.irises.pbox3.room.dao.AssignDao;
import top.irises.pbox3.room.db.PboxDb;
import top.irises.pbox3.room.domain.Assign;

public class AssignRepo {
    private LiveData<List<Assign>> allAssignAlive;
    private AssignDao assignDao;

    public AssignRepo(Context context){
        PboxDb db = PboxDb.getDatabase(context.getApplicationContext());
        this.assignDao = db.getAssignDao();
        this.allAssignAlive = assignDao.getAllAssignLive();
    }

    public LiveData<List<Assign>> getAllAssignAlive(){
        return allAssignAlive;
    }

    public void insertItem(Assign... assigns){
        new InsertAsyncTask(assignDao).execute(assigns);
    }

    public void updateItem(Assign... assigns){
        new UpdateAsyncTask(assignDao).execute(assigns);
    }

    public void deleteItem(Assign... assigns){
        new DeleteAsyncTask(assignDao).execute(assigns);
    }

    public void truncate(){
        new DeleteAllAsyncTask(assignDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Assign,Void,Void>{
        private AssignDao assignDao;
        InsertAsyncTask(AssignDao assignDao){
            this.assignDao = assignDao;
        }
        @Override
        protected Void doInBackground(Assign... assigns) {
            assignDao.insertItem(assigns);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Assign, Void, Void> {
        private AssignDao assignDao;

        UpdateAsyncTask(AssignDao assignDao){
            this.assignDao = assignDao;
        }

        @Override
        protected Void doInBackground(Assign... assigns) {
            assignDao.updateItem(assigns);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Assign, Void, Void> {
        AssignDao assignDao;
        DeleteAsyncTask(AssignDao assignDao){
            this.assignDao = assignDao;
        }

        @Override
        protected Void doInBackground(Assign... assigns) {
            assignDao.deleteItem(assigns);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private AssignDao assignDao;

        DeleteAllAsyncTask(AssignDao assignDao) {
            this.assignDao = assignDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            assignDao.turncate();
            return null;
        }

    }
}
