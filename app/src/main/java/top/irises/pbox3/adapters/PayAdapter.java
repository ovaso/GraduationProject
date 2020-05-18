package top.irises.pbox3.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import top.irises.pbox3.R;
import top.irises.pbox3.room.domain.Pay;
import top.irises.pbox3.utils.DateConverter;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayViewHolder> {

    List<Pay> pays = new ArrayList<Pay>();
    String coinUnit;

    public void setPays(List<Pay> pays) {
        this.pays = pays;
    }

    @NonNull
    @Override
    public PayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        coinUnit = parent.getContext().getResources().getString(R.string.coin_unit);
        View itemView = inflater.inflate(R.layout.pay_list,parent,false);
        return new PayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PayViewHolder holder, int position) {
        Pay pay = pays.get(position);
        if (pay.getExpend()>0) {
            holder.expend.setTextColor(Color.parseColor("#f50057"));
        }
        if (pay.getExpend()<0) {
            holder.expend.setTextColor(Color.parseColor("#00e676"));
        }
        holder.expend.setText(coinUnit + String.valueOf(pay.getExpend()));
        holder.date.setText(DateConverter.getSimpleDate(pay.getCreateTime()));
        holder.ps.setText(pay.getPs());
    }

    @Override
    public int getItemCount() {
        return pays.size();
    }

    static class PayViewHolder extends RecyclerView.ViewHolder{
        TextView ps,expend,date;
        ConstraintLayout layout;
        public PayViewHolder(@NonNull View itemView) {
            super(itemView);
            ps = itemView.findViewById(R.id.payment_ps);
            expend = itemView.findViewById(R.id.expend_msg);
            date = itemView.findViewById(R.id.payment_date);
        }
    }
}
