package top.irises.pbox3.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

import top.irises.pbox3.R;
import top.irises.pbox3.activities.AddCont;
import top.irises.pbox3.room.domain.Cont;

public class ContAdapter extends RecyclerView.Adapter<ContAdapter.ContViewHolder> {

    List<Cont> conts = new ArrayList<>();
    Context context;

    public void setConts(List<Cont> conts) {
        this.conts = conts;
    }

    @NonNull
    @Override
    public ContViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cont_list,parent,false);
        this.context = parent.getContext();

        return new ContViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContViewHolder holder, int position) {

        Cont cont = conts.get(position);
        holder.contTel.setText(cont.getTel());
        holder.contName.setText(cont.getName());
        holder.contDis.setText("+86");
        holder.arr.setOnClickListener(v->{
            Intent intent = new Intent(context, AddCont.class);
            intent.putExtra("cont_item",cont);
            context.startActivity(intent);
        });
        holder.contTel.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+cont.getTel()));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return conts.size();
    }

    static class ContViewHolder extends RecyclerView.ViewHolder {

        ImageView image,arr;
        TextView contName,contDis,contTel;

        public ContViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cont_head);
            contName = itemView.findViewById(R.id.cont_name);
            contDis = itemView.findViewById(R.id.cont_dis);
            contTel = itemView.findViewById(R.id.cont_tel);
            arr = itemView.findViewById(R.id.img_cont_list_arr);
        }
    }
}
