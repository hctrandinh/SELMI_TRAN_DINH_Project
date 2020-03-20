package fr.esilv.selmi_tran_dinh_project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyRecyclerViewAdapterImage extends RecyclerView.Adapter<MyRecyclerViewAdapterImage.ViewHolderImage> {

    private ArrayList<String> home_url;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // Here you give data to the constructor.
    MyRecyclerViewAdapterImage(Context context, ArrayList<String> home_url) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.home_url = home_url;
    }

    // Inflates the column layout from xml when needed.
    @Override
    public ViewHolderImage onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cards_column, parent, false);
        return new ViewHolderImage(view);
    }

    // Binds the data to the TextView in each row.
    @Override
    public void onBindViewHolder(ViewHolderImage holder, int position) {
        String image = home_url.get(position);
        holder.setDetails(context, image);
    }

    // Total number of rows.
    @Override
    public int getItemCount() {
        return home_url.size();
    }


    // Stores and recycles views as they are scrolled off screen.
    public class ViewHolderImage extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img_home_card;

        ViewHolderImage(View itemView) {
            super(itemView);
            img_home_card = (ImageView) itemView.findViewById(R.id.img_home_card);
            itemView.setOnClickListener(this);
        }

        void setDetails(Context context, String image) {
            Drawable myDrawable;
            if(image == "home1")
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home1);
            }
            else if(image == "home2")
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home2);
            }
            else if(image == "home3")
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home3);
            }
            else if(image == "home4")
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home4);
            }
            else if(image == "home5")
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home5);
            }
            else
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home6);
            }
            img_home_card.setImageDrawable(myDrawable);
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