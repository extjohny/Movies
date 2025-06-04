package com.example.movies.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.data.trailer.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private OnTrailerClickListener onTrailerClickListener;

    private List<Trailer> trailers = new ArrayList<>();

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.trailer_item,
                        parent,
                        false
                );
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.trailerNameTextView.setText(trailer.getName());
        if (onTrailerClickListener != null) {
            holder.itemView.setOnClickListener(view ->
                    onTrailerClickListener.onTrailerClick(trailer));
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView trailerNameTextView;
        ImageView goToTrailerButton;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerNameTextView = itemView.findViewById(R.id.textViewTrailerName);
            goToTrailerButton = itemView.findViewById(R.id.buttonGoToTrailer);
        }
    }

    public interface OnTrailerClickListener {

        void onTrailerClick(Trailer trailer);
    }
}
