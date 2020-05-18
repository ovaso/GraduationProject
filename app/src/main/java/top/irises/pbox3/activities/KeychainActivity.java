package top.irises.pbox3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

import java.util.List;

import top.irises.pbox3.R;
import top.irises.pbox3.adapters.KeAdapter;
import top.irises.pbox3.databinding.ActivityKeychainBinding;
import top.irises.pbox3.room.domain.Ke;
import top.irises.pbox3.vms.KeViewModel;

public class KeychainActivity extends AppCompatActivity {
    private ActivityKeychainBinding binding;
    private KeAdapter adapter;
    private KeViewModel mViewModel;
    private List<Ke> keys;
    private MutableLiveData<Boolean> pk;
    private LiveData<List<Ke>> allKeysAlive;
    private SearchView searchView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.keychain_tool_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(500);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                Log.d("SEARCH",pattern);
                if (allKeysAlive.hasObservers()) {
                    allKeysAlive.removeObservers(KeychainActivity.this);
                }
                allKeysAlive = mViewModel.getFilteredAlived(pattern);
                allKeysAlive.observe(KeychainActivity.this, new Observer<List<Ke>>() {
                    @Override
                    public void onChanged(List<Ke> kes) {
                        int temp = adapter.getItemCount();
                        keys = kes;
                        if (temp != kes.size()) {
                            adapter.setKeys(keys);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                return true;
            }
        });
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("钥匙圈");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_keychain);
        binding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(KeychainActivity.this).get(KeViewModel.class);
        pk = mViewModel.getPk();
        pk.setValue(false);
        SharedPreferences shp = getSharedPreferences("conf",MODE_PRIVATE);
        adapter = new KeAdapter();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        allKeysAlive = mViewModel.getAllKeAlive();
        allKeysAlive.observe(this, kes -> {
            int tmp = adapter.getItemCount();
            keys = kes;
            if (kes.size() != tmp){
                adapter.setKeys(keys);
                adapter.notifyDataSetChanged();
            }
        });

        pk.observe(KeychainActivity.this,pk->{
            if (pk){
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.fabKeAdd.setVisibility(View.VISIBLE);
                binding.editText.setVisibility(View.INVISIBLE);
                binding.button.setVisibility(View.INVISIBLE);
                binding.passTip.setVisibility(View.INVISIBLE);
            }else{
                binding.recyclerView.setVisibility(View.INVISIBLE);
                binding.fabKeAdd.setVisibility(View.INVISIBLE);
                binding.editText.setVisibility(View.VISIBLE);
                binding.button.setVisibility(View.VISIBLE);
                binding.passTip.setVisibility(View.VISIBLE);
            }
        });
        binding.button.setOnClickListener(v->{
            if (binding.editText.getText().toString().equals(shp.getString("k","0"))) {
                mViewModel.getPk().setValue(true);
            }else{
                binding.passTip.setText("密码错误");
            }
        });

        //SWIPE TO DELTETE ItemTouchHelper
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Ke keyToDelete = keys.get(viewHolder.getAdapterPosition());
                mViewModel.deleteItem(keyToDelete);
            }

            //删除图标
            Drawable icon = ContextCompat.getDrawable(KeychainActivity.this,R.drawable.ic_delete_black_24dp);
            Drawable background = new ColorDrawable(Color.RED);
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;

                int iconLeft,iconRight,iconTop,iconBottom;
                int backTop,backBottom,backLeft,backRight;
                backTop = itemView.getTop();
                backBottom = itemView.getBottom();
                iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
                iconBottom = iconTop + icon.getIntrinsicHeight();
                if (dX > 0) {
                    backLeft = itemView.getLeft();
                    backRight = itemView.getLeft() + (int)dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconLeft = itemView.getLeft() + iconMargin ;
                    iconRight = iconLeft + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                } else if (dX < 0){
                    backRight = itemView.getRight();
                    backLeft = itemView.getRight() + (int)dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconRight = itemView.getRight()  - iconMargin;
                    iconLeft = iconRight - icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                } else {
                    background.setBounds(0,0,0,0);
                    icon.setBounds(0,0,0,0);
                }
                background.draw(c);
                icon.draw(c);
            }
        }).attachToRecyclerView(binding.recyclerView);

        binding.fabKeAdd.setOnClickListener(v->{
            Intent intent = new Intent(this,AddKey.class);
            startActivity(intent);
        });
    }


}
