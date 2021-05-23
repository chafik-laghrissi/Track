package com.example.track;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private ArrayList<Track> tracks = new ArrayList<>();
    private Context context;

    public TrackAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.trackName.setText(tracks.get(position).getName());
        holder.trackDate.setText(tracks.get(position).getDate());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent =new Intent(context,DetailActivity.class);
                intent.putExtra("longitude", tracks.get(position).getLongitude());
                intent.putExtra("latitude", tracks.get(position).getLatitude());
                intent.putExtra("altitude", tracks.get(position).getAltitude());
                intent.putExtra("name", tracks.get(position).getName());
                intent.putExtra("imageUri", tracks.get(position).getImageUri());
                intent.putExtra("date", tracks.get(position).getDate());
                intent.putExtra("id", tracks.get(position).getId());
                context.startActivity(intent);

            }
        });
//        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                holder.btn.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trackName, trackDate;
        private MaterialCardView parent;

        //        private ImageButton btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.trackName = itemView.findViewById(R.id.trackItemName);
            this.trackDate = itemView.findViewById(R.id.itemDate);
            this.parent = itemView.findViewById(R.id.container);
//            this.btn=itemView.findViewById(R.id.deleteIcon);
        }
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }
}
