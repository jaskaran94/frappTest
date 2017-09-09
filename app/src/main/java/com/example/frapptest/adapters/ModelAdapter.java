package com.example.frapptest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.frapptest.R;
import com.example.frapptest.models.Model;
import com.example.frapptest.utilities.CircleTransform;
import com.example.frapptest.utilities.Utils;

import java.util.List;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    List<Model> modelList;
    Context mContext;
    CircleTransform transform;
    OnMarkFavorite markFavoriteListener;
    String fType;

    public ModelAdapter(List<Model> models, Context context, String fragment){
        this.modelList = models;
        this.mContext = context;
        transform = new CircleTransform(context);
        markFavoriteListener = (OnMarkFavorite) context;
        this.fType = fragment;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.model_item_row, parent, false));
        if (this.fType.equalsIgnoreCase("A")) {
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Model model = modelList.get(position);
                        model.setSelected(!model.isSelected());
                        markFavoriteListener.onMarked(model, model.isSelected());
                        notifyItemChanged(position);
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Model model = modelList.get(position);
        Glide.with(mContext)
                .load(model.getImageUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(transform)
                .into(holder.image);
        if (holder.favorite.getVisibility() == View.VISIBLE) {
            if (model.isSelected()) {
                holder.favorite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_marked));
            } else {
                holder.favorite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_unmarked));
            }
        }
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDesc());
        holder.type.setText(model.getType());
        holder.viewCount.setText(String.format(
                mContext.getString(R.string.view_count),
                Utils.convertToString(
                        model.getViewCount(), 0
                )
        ));
    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, desc, viewCount, type;
        ImageView image, favorite;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc_text);
            image = (ImageView) itemView.findViewById(R.id.user_image);
            viewCount = (TextView) itemView.findViewById(R.id.view_count);
            type = (TextView) itemView.findViewById(R.id.type);
            favorite = (ImageView) itemView.findViewById(R.id.mark_fav);
            if (fType.equalsIgnoreCase("A")) favorite.setVisibility(View.VISIBLE);
        }
    }
    public interface OnMarkFavorite{
        void onMarked(Model model, boolean mark);
    }
}
