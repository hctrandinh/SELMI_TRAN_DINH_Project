package fr.esilv.selmi_tran_dinh_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//General Pokemon list adapter.

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Pokemon> pokemon;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Here you give data to the constructor.
    MyRecyclerViewAdapter(Context context, ArrayList<Pokemon> pokemon) {
        this.mInflater = LayoutInflater.from(context);
        this.pokemon = pokemon;
    }

    // Inflates the row layout from xml when needed.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cards, parent, false);
        return new ViewHolder(view);
    }

    // Binds the data to the TextView in each row.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon information = pokemon.get(position);
        holder.setDetails(information);
    }

    // Total number of rows.
    @Override
    public int getItemCount() {
        return pokemon.size();
    }


    // Stores and recycles views as they are scrolled off screen.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName, txtUrl;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUrl = itemView.findViewById(R.id.txtUrl);
            itemView.setOnClickListener(this);
        }

        void setDetails(Pokemon pokemon) {
            txtName.setText(pokemon.getName());
            txtUrl.setText(pokemon.getUrl());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Parent activity will implement this method to respond to click events.
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}