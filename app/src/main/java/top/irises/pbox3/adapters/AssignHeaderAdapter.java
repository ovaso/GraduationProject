package top.irises.pbox3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import top.irises.pbox3.R;

public class AssignHeaderAdapter extends RecyclerView.Adapter<AssignHeaderAdapter.AssignHeaderViewHolder> {

    @NonNull
    @Override
    public AssignHeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.assign_header, parent, false);
        return new AssignHeaderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignHeaderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class AssignHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public AssignHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.assign_header_date);
        }
    }
}
