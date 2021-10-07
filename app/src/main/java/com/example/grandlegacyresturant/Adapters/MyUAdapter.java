package com.example.grandlegacyresturant.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grandlegacyresturant.Model.userModel;
import com.example.grandlegacyresturant.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class MyUAdapter extends FirebaseRecyclerAdapter<userModel,MyUAdapter.myUViewholder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyUAdapter(@NonNull @NotNull FirebaseRecyclerOptions<userModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myUViewholder holder, int position, @NonNull @NotNull userModel model) {
        holder.uname.setText(model.getUname());
        holder.ufeedback.setText(model.getUfeedback());
        holder.urateCount.setText(model.getUrateCount());


    }

    @NonNull
    @NotNull
    @Override
    public myUViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new myUViewholder(view);
    }

    class myUViewholder extends RecyclerView.ViewHolder{

        TextView uname, ufeedback ,urateCount;

        public myUViewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            uname = (TextView)itemView.findViewById(R.id.unametxt);
            ufeedback = (TextView)itemView.findViewById(R.id.ufeedbacktxt);
            urateCount = (TextView)itemView.findViewById(R.id.urateCounttxt);



        }
    }


}
