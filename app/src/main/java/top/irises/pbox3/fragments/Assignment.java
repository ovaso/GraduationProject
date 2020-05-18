package top.irises.pbox3.fragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import top.irises.pbox3.R;
import top.irises.pbox3.activities.AddAssign;
import top.irises.pbox3.adapters.AssignAdapter;
import top.irises.pbox3.base.Global;
import top.irises.pbox3.room.domain.Assign;
import top.irises.pbox3.vms.AssignmentViewModel;

public class Assignment extends Fragment {

    private AssignmentViewModel mViewModel;
    private FragmentActivity activity;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private AssignAdapter adapter;
    private List<Assign> allAssign;
    private DividerItemDecoration dividerItemDecoration;
    private boolean orderSet;


    public static Assignment newInstance() {
        return new Assignment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.assignment_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.f_menu,menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        orderSet = Global.GLOBAL_ORDER;
        mViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        //处理组件关联
        drawerLayout = activity.findViewById(R.id.id_drawer_layout);
        toolbar = activity.findViewById(R.id.toolbar_assign);
        toolbar.setTitle("任务");
        toolbar.inflateMenu(R.menu.f_menu);
        toolbar.setOnMenuItemClickListener(e->{
            switch (e.getItemId()){
                case R.id.assign_sort:
                    //写在SHP里,配置初始升降序
                    if (Global.GLOBAL_ORDER){
                        List<Assign> collect = mViewModel.getAllAssignLive().getValue().stream().sorted((a, b) -> {
                            return Math.toIntExact(a.getCreateTime() - b.getCreateTime());
                        }).collect(Collectors.toList());
                        allAssign = allAssign.stream().sorted((a,b)->{
                            return Math.toIntExact(a.getCreateTime() - b.getCreateTime());
                        }).collect(Collectors.toList());
                        adapter.setAssigns(collect);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"旧->新",Toast.LENGTH_SHORT).show();
                        Global.GLOBAL_ORDER = !Global.GLOBAL_ORDER;
                    }else{
                        List<Assign> collect = mViewModel.getAllAssignLive().getValue().stream().sorted((a, b) -> {
                            return Math.toIntExact(b.getCreateTime() - a.getCreateTime());
                        }).collect(Collectors.toList());
                        allAssign = allAssign.stream().sorted((a,b)->{
                            return Math.toIntExact(a.getCreateTime() - b.getCreateTime());
                        }).collect(Collectors.toList());
                        adapter.setAssigns(collect);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"新->旧",Toast.LENGTH_SHORT).show();
                        Global.GLOBAL_ORDER = !Global.GLOBAL_ORDER;
                    }
                    break;
                case R.id.assign_delete_expired:
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("删除过期数据");
                    long current = new Date().getTime();
                    builder.setPositiveButton("确认",(dialog,which)->{
                        List<Assign> filteredList = mViewModel.getAllAssignLive().getValue().stream().filter(item -> {
                            if (item.getEndTime() != 0 && item.getEndTime() < current)
                                mViewModel.deleteItem(item);
                            return true;
                        }).collect(Collectors.toList());
                        for (Assign assign : filteredList) {
                            Log.d("FILTERED",assign.toString());
                        }
                    });
                    builder.setNegativeButton("取消",(dialog,which)->{

                    });
                    builder.create();
                    builder.show();
                    break;
                case R.id.assign_delete_all:
                    builder = new AlertDialog.Builder(requireActivity());
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
            }
            return true;
        });

        //设置toggle
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        TextView tip = activity.findViewById(R.id.assign_tip);
        fab = activity.findViewById(R.id.add_assign);
        RecyclerView recyclerView = activity.findViewById(R.id.assign_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dividerItemDecoration = new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        adapter = new AssignAdapter();
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(e->{
            Intent intent = new Intent(getActivity(), AddAssign.class);
            startActivity(intent);
        });

        //ViewModel添加观察者
        mViewModel.getAllAssignLive().observe(getActivity(), new Observer<List<Assign>>() {
            @Override
            public void onChanged(List<Assign> assigns) {
                int tmp = adapter.getItemCount();
                adapter.setAssigns(assigns);
                allAssign = assigns;
                if(adapter.getItemCount() == 0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    tip.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    tip.setVisibility(View.INVISIBLE);
                }
                if (assigns.size()!=tmp) {
                    if (!orderSet){
                        List<Assign> collect = mViewModel.getAllAssignLive().getValue().stream().sorted((a, b) -> {
                            return Math.toIntExact(a.getCreateTime() - b.getCreateTime());
                        }).collect(Collectors.toList());
                        allAssign = allAssign.stream().sorted((a,b)->{
                            return Math.toIntExact(a.getCreateTime() - b.getCreateTime());
                        }).collect(Collectors.toList());
                        adapter.setAssigns(collect);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mViewModel.getOrder().observe(getViewLifecycleOwner(),(order)->{
            if (order){
                orderSet = order;
            }else{
                orderSet = order;
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
                Assign assignToDelete = allAssign.get(viewHolder.getAdapterPosition());
                mViewModel.deleteItem(assignToDelete);
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
    }



    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getOrder().setValue(Global.GLOBAL_ORDER);
    }
}
