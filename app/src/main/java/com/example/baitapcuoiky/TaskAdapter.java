package com.example.baitapcuoiky;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapcuoiky.model.*;

import java.util.List;

public class TaskAdapter extends  RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private List<Task> tasks;
    private OnTaskListener onTaskListener;

    public TaskAdapter(Context context, OnTaskListener onTaskListener){
        this.context = context;
        this.onTaskListener = onTaskListener;
    }

    public void setData(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view, onTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task t = tasks.get(position);
        if(t == null){
            return;
        }
        holder.titleTask.setText(t.getTitle());
        holder.desTask.setText(t.getDes());
        holder.dateTask.setText(t.getDate());
        holder.statusTask.setText(t.getStatus() == 0 ? "Chưa hoàn thành" : "Đã hoàn thành");
    }

    @Override
    public int getItemCount() {
        if(tasks != null){
            return tasks.size();
        }
        return 0;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTask, desTask, dateTask, statusTask;
        OnTaskListener onTaskListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskListener onTaskListener) {
            super(itemView);

            titleTask = itemView.findViewById(R.id.titleTask);
            desTask = itemView.findViewById(R.id.desTask);
            dateTask = itemView.findViewById(R.id.dateTask);
            statusTask = itemView.findViewById(R.id.statusTask);
            this.onTaskListener = onTaskListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTaskListener.onTaskClick(getAdapterPosition());
        }
    }

    public interface OnTaskListener {
        void onTaskClick(int position);
    }
}
