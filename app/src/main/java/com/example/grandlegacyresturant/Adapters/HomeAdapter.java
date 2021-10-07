package com.example.grandlegacyresturant.Adapters;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grandlegacyresturant.FoodDetails;
import com.example.grandlegacyresturant.GlobalVal;
import com.example.grandlegacyresturant.Home_Fragment;
import com.example.grandlegacyresturant.Model.FoodModel;
import com.example.grandlegacyresturant.R;
import com.example.grandlegacyresturant.profileUpdateActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class  HomeAdapter extends FirebaseRecyclerAdapter<FoodModel,HomeAdapter.myViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HomeAdapter(FirebaseRecyclerOptions<FoodModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull HomeAdapter.myViewHolder holder, int position,@NonNull FoodModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(new StringBuilder("Rs.").append(model.getPrice()));
        //private String foodID = "";

        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(),FoodDetails.class);
                myIntent.putExtra("pid",model.getPid());
                v.getContext().startActivity(myIntent);
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_home,parent,false);
        return new myViewHolder(view);
    }


    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,price;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.nametext);
            price = (TextView)itemView.findViewById(R.id.pricetext);




        }
    }


}
