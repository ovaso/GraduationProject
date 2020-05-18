package top.irises.pbox3.adapters;

import android.app.job.JobInfo;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import top.irises.pbox3.R;
import top.irises.pbox3.activities.AddAssign;
import top.irises.pbox3.room.domain.Assign;
import top.irises.pbox3.utils.DateConverter;

//1
//6
public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.AssignViewHolder> {
    //CHANGE
    private boolean showCheckbox;
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    private Context context;

    public boolean isShowCheckBox() {
        return showCheckbox;
    }
    public void setShowCheckBox(boolean showCheckbox) {
        this.showCheckbox = showCheckbox;
    }


    //8
    List<Assign> assigns = new ArrayList<Assign>();
    public void setAssigns(List<Assign> assigns) {
        this.assigns = assigns;
    }

    //7
    //ViewHolder创建时的回调
    @NonNull
    @Override
    public AssignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //10
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        this.context = parent.getContext();
        View itemView = inflater.inflate(R.layout.assign_list,parent,false);
//CHANGE:ADD CLICK EVENTS
        final AssignViewHolder holder = new AssignViewHolder(itemView);
        return holder;
    }

    //7
    //ViewHolder和RecyclerView绑定时回调
    @Override
    public void onBindViewHolder(@NonNull AssignViewHolder holder, int position) {
        //11
        Assign assign = assigns.get(position);
        holder.createTime.setText(DateConverter.getSimpleDate(assign.getCreateTime()));
        holder.title.setText(assign.getTitle());

        holder.itemView.setOnClickListener(i->{
            Intent intent = new Intent(context,AddAssign.class);
            intent.putExtra("assign_item",assign);
            context.startActivity(intent);
        });
    }

    //7
    //返回列表数据个数
    @Override
    public int getItemCount() {
        //9
        return assigns.size();
    }

    //2
    static class AssignViewHolder extends RecyclerView.ViewHolder {

        //4
        TextView title,createTime;
        //3
        public AssignViewHolder(@NonNull View itemView) {
            super(itemView);
            //5
            title = itemView.findViewById(R.id.assign_title);
            createTime = itemView.findViewById(R.id.assign_createtime);
        }
    }
}
