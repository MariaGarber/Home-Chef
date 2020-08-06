/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.homechef;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterIngredient extends RecyclerView.Adapter<RecyclerViewAdapterIngredient.MyViewHolder> {
    private Context mContext;
    private List<Ingredient> mData;
    public static List<String> ingredientsList;

    RecyclerViewAdapterIngredient(Context mContext, List<Ingredient> mData) {
        this.mContext = mContext;
        this.mData = mData;
        ingredientsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewAdapterIngredient.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_ingredient, parent, false);
        return new RecyclerViewAdapterIngredient.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterIngredient.MyViewHolder holder, final int position) {
        holder.tv_ingredient_name.setText(mData.get(position).getName());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.img_ingredient_thumbnail);
        if (mData.get(position).isSelected()) holder.cardView.setCardBackgroundColor(Color.parseColor("#EAEDED"));
        else holder.cardView.setCardBackgroundColor(Color.WHITE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mData.get(position).isSelected()) {
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#EAEDED"));
                    ingredientsList.add(mData.get(position).getName());
                } else if (mData.get(position).isSelected()) {
                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                    ingredientsList.remove(mData.get(position).getName());
                }
                mData.get(position).setSelected();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ingredient_name;
        ImageView img_ingredient_thumbnail;
        LinearLayout layoutl;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_ingredient_name = (TextView) itemView.findViewById(R.id.ingredient_name);
            img_ingredient_thumbnail = (ImageView) itemView.findViewById(R.id.ingredient_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview_ingredient);
            layoutl = itemView.findViewById(R.id.ingredient_layout);
        }
    }
}
