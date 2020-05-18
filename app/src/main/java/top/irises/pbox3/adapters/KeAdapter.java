package top.irises.pbox3.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import top.irises.pbox3.R;
import top.irises.pbox3.base.Global;
import top.irises.pbox3.room.domain.Ke;
import top.irises.pbox3.utils.DateConverter;

public class KeAdapter extends RecyclerView.Adapter<KeAdapter.KeViewHolder> {
    private List<Ke> keys = new ArrayList<>();

    public void setKeys(List<Ke> keys) {
        this.keys = keys;
    }

    @NonNull
    @Override
    public KeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.ke_list,parent, false);
        return new KeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KeViewHolder holder, int position) {
        Ke ke = keys.get(position);

        if (ke.getExpires()>0 && ke.getExpires()<DateConverter.getTimestamp()){
            holder.title.setTextColor(Color.parseColor("#afafaf"));
            holder.acc.setTextColor(Color.parseColor("#afafaf"));
            holder.expires.setTextColor(Color.parseColor("#afafaf"));
            holder.aSwitch.setChecked(true);
            holder.aSwitch.setEnabled(true);
        }
        if (Global.GLOBAL_SHOW_PSW){
            holder.expires.setText(ke.getKey());
        }
        holder.aSwitch.setChecked(Global.GLOBAL_SHOW_PSW);
        holder.expires.setText(DateConverter.getSimpleDate(ke.getExpires()));
        holder.type.setText(ke.getType());
        holder.title.setText(ke.getPs());
        holder.no.setText(String.valueOf(position+1));
        holder.acc.setText(ke.getAccount());

        holder.aSwitch.setOnCheckedChangeListener((buttonView,isChecked)->{
            if(isChecked){
                holder.expires.setText(ke.getKey());
            }else{
                holder.expires.setText(DateConverter.getSimpleDate(ke.getExpires()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    static class KeViewHolder extends RecyclerView.ViewHolder {
        TextView no,title,type,acc,expires;
        Switch aSwitch;
        public KeViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.ke_no);
            title = itemView.findViewById(R.id.ke_title);
            type = itemView.findViewById(R.id.ke_type);
            acc = itemView.findViewById(R.id.ke_acc);
            expires = itemView.findViewById(R.id.ke_expires);
            aSwitch = itemView.findViewById(R.id.ke_show);
        }
    }
}
