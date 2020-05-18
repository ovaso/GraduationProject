package top.irises.pbox3.fragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.adapters.SeekBarBindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.BreakIterator;
import java.util.List;

import top.irises.pbox3.R;
import top.irises.pbox3.activities.AddCont;
import top.irises.pbox3.adapters.ContAdapter;
import top.irises.pbox3.room.domain.Cont;
import top.irises.pbox3.room.repos.ContRepo;
import top.irises.pbox3.vms.ContactViewModel;

public class Contact extends Fragment {

    private FragmentActivity activity;
    private ContactViewModel mViewModel;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextView tip;
    private SearchView searchView;
    private DividerItemDecoration dividerItemDecoration;
    private ContRepo repo;

    //MAIN DATA
    private LiveData<List<Cont>> filtered;
    private List<Cont> allContact;


    RecyclerView recyclerView;
    ContAdapter adapter;

    public static Contact newInstance() {
        return new Contact();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //GLOBAL
        activity = getActivity();
        mViewModel = new ViewModelProvider(activity).get(ContactViewModel.class);

        //COMPONENTS
        tip = activity.findViewById(R.id.cont_tip);
        fab = activity.findViewById(R.id.fab_cont_add);
        recyclerView = activity.findViewById(R.id.cont_list);

        //TOOLBAR
        toolbar = activity.findViewById(R.id.toolbar_contact);
        toolbar.setTitle("人脉");
        toolbar.inflateMenu(R.menu.cont_menu);

        //TOGGLE
        drawerLayout = activity.findViewById(R.id.id_drawer_layout);
        toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //VARIABLES
        adapter = new ContAdapter();
        dividerItemDecoration = new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        filtered = mViewModel.getAllContAlive();

        repo = new ContRepo(activity);
        recyclerView.addItemDecoration(dividerItemDecoration);
        searchView = activity.findViewById(R.id.app_bar_search_cont);
        searchView.setMaxWidth(1000);


        //PRESETS
        //TOOLBAR FALLBACKS
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.app_bar_search_cont:
                    break;
                case R.id.cont_menu_deleteAll:
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("警告");
                    builder.setMessage("将删除所有数据!不可找回!");
                    builder.setPositiveButton("确认",(dialog,which)->{
                        mViewModel.truncate();
                    });
                    builder.setNegativeButton("取消",(dialog,which)->{
                    });
                    builder.create();
                    builder.show();
                    break;
                default:
                    break;
            }
            return true;
        });

        //SEARCH VIEW
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                filtered.removeObservers(getViewLifecycleOwner());
                filtered = mViewModel.getFilteredAlived(pattern);
                filtered.observe(getViewLifecycleOwner(),conts -> {
                    int tmp = adapter.getItemCount();
                    allContact = conts;
                    if(tmp != conts.size()){
                        adapter.setConts(conts);
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
            }});

        //OBSERVER
        filtered.observe(activity,conts->{
            int tmp = adapter.getItemCount();
            adapter.setConts(conts);
            allContact = conts;
            if(adapter.getItemCount() == 0){
                recyclerView.setVisibility(View.INVISIBLE);
                tip.setVisibility(View.VISIBLE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
                tip.setVisibility(View.INVISIBLE);
            }
            if (conts.size()!=tmp) {
                recyclerView.smoothScrollBy(0,200);
                adapter.notifyDataSetChanged();
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
                Cont contToDelete = allContact.get(viewHolder.getAdapterPosition());
                mViewModel.deleteItem(contToDelete);
            }

            //删除图标
            Drawable icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_delete_black_24dp);
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
        }).attachToRecyclerView(recyclerView);

        //FAB
        fab.setOnClickListener(v->{
            Intent intent = new Intent(activity, AddCont.class);
            activity.startActivity(intent);
        });

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
