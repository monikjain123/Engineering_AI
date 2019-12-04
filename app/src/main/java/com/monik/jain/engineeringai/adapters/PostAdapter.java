package com.monik.jain.engineeringai.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.monik.jain.engineeringai.R;
import com.monik.jain.engineeringai.models.Hit;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Hit> hits;

    private onItemClick onItemClick;

    public PostAdapter.onItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(PostAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public PostAdapter(List<Hit> hits) {
        this.hits = hits;
    }

    public PostAdapter() {
    }

    public void setItemList(List<Hit> hits) {
        this.hits = hits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hit hit = hits.get(position);
        holder.txtTitle.setText(hit.getTitle());
        holder.txtCreatedAt.setText(hit.getCreatedAt());
        holder.itemView.setOnClickListener(holder);
        holder.swtActivatePost.setChecked(hit.isEnabled());

    }

    @Override
    public int getItemCount() {
        if (hits != null && hits.size() > 0) {
            return hits.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitle;
        TextView txtCreatedAt;
        SwitchCompat swtActivatePost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            swtActivatePost = itemView.findViewById(R.id.swtActivatePost);
            swtActivatePost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    swtActivatePost.setChecked(isChecked);
                    hits.get(getAdapterPosition()).setEnabled(isChecked);
                    if(onItemClick != null){
                        onItemClick.onClick(hits);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

            swtActivatePost.setChecked(!swtActivatePost.isChecked());


        }
    }

    public interface onItemClick {
        public void onClick(List<Hit> hits);
    }
}
