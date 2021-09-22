package com.rizoma.myproducts.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rizoma.myproducts.DetailActivity;
import com.rizoma.myproducts.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Product> localDataSet;
    private Context context;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDesc;
        private final TextView tvPrice;
        private final TextView tvStock;
        private final ImageView image;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvName = (TextView) view.findViewById(R.id.tvName);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvStock = (TextView) view.findViewById(R.id.tvStock);
            image = (ImageView) view.findViewById(R.id.img);
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public Adapter(List<Product> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Product product = localDataSet.get(position);
        viewHolder.tvName.setText( product.getName());
        if (product.getDesc().length()>70){
         String   desc = product.getDesc().substring(0,70);
            viewHolder.tvDesc.setText( desc);
        }else {
            viewHolder.tvDesc.setText( product.getDesc());
        }
        viewHolder.tvPrice.setText( product.getPrice()+"$");
        if (product.isInStock()){
            viewHolder.tvStock.setText("In Stock");
            viewHolder.tvStock.setTextColor(Color.parseColor("#FF99CC00"));

        }else {
            viewHolder.tvStock.setText("Out Of Stock");
            viewHolder.tvStock.setTextColor(Color.parseColor("#FFE91E63"));
        }
        try {
            Picasso.get().load(product.getImage()).into(viewHolder.image);
        }catch (Exception e){

        }
        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", product.getId());
            context.startActivity(intent);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
