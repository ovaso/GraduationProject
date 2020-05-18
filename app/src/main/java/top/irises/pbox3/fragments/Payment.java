package top.irises.pbox3.fragments;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import top.irises.pbox3.R;
import top.irises.pbox3.activities.AddPay;
import top.irises.pbox3.adapters.PayAdapter;
import top.irises.pbox3.room.domain.Pay;
import top.irises.pbox3.vms.PaymentViewModel;

public class Payment extends Fragment {

    private PaymentViewModel mViewModel;
    FragmentActivity activity;
    Toolbar toolbar;
    AlertDialog.Builder builder;
    TextView tip;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    PayAdapter adapter;
    List<Pay> allPay;

    //MAIN DATA

    public static Payment newInstance() {
        return new Payment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payment_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //GLOBAL

        activity = getActivity();
        mViewModel = new ViewModelProvider(activity).get(PaymentViewModel.class);
        // TODO: Use the ViewModel
        //TOOLBAR
        toolbar = activity.findViewById(R.id.toolbar_payment);
        toolbar.setTitle("账单");
        toolbar.inflateMenu(R.menu.pay_menu);
        //TOGGLE
        drawerLayout = activity.findViewById(R.id.id_drawer_layout);
        toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //COMPONENTS

        tip = activity.findViewById(R.id.payment_tip);
        fab = activity.findViewById(R.id.fab_pay_add);
        recyclerView = activity.findViewById(R.id.pay_list);
        adapter = new PayAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        //LOGIC

        fab.setOnClickListener(i->{
            Intent intent = new Intent(activity, AddPay.class);
            activity.startActivity(intent);
        });
        toolbar.setOnMenuItemClickListener(item->{
            switch (item.getItemId()){
                case R.id.delete_all_pay:
                    builder = new AlertDialog.Builder(activity);
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

        //ViewModel Observer
        mViewModel.getAllPayAlive().observe(getViewLifecycleOwner(), pays->{
            int tmp = adapter.getItemCount();
            adapter.setPays(pays);
            allPay = pays;
            if(adapter.getItemCount() == 0){
                recyclerView.setVisibility(View.INVISIBLE);
                tip.setVisibility(View.VISIBLE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
                tip.setVisibility(View.INVISIBLE);
            }
            if (pays.size()!=tmp) {
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
                Pay payToDelete = allPay.get(viewHolder.getAdapterPosition());
                mViewModel.deleteItem(payToDelete);
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
        toolbar.setTitle("账单");
    }
}
