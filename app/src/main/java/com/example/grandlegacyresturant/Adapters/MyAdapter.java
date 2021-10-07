package com.example.grandlegacyresturant.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grandlegacyresturant.Model.Model;
import com.example.grandlegacyresturant.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class MyAdapter extends FirebaseRecyclerAdapter<Model,MyAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder,final int position, @NonNull @NotNull Model model) {
        holder.name.setText(model.getName());
        holder.feedback.setText(model.getFeedback());
        holder.rateCount.setText(model.getRateCount());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.feed_update_popup))
                        .setExpanded(true, 1100)
                        .create();

              //  dialogPlus.show();

                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.upName);
                EditText feedback = view.findViewById(R.id.upAdd1);
                EditText rateCount = view.findViewById(R.id.uprateCount);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                feedback.setText(model.getFeedback());
                rateCount.setText(model.getRateCount());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object>map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("feedback",feedback.getText().toString());
                        map.put("rateCount",rateCount.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Feedback Updated Successfully", Toast.LENGTH_SHORT).show();
                                       dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });



                    }
                });

            }
        });



        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure??");
                builder.setTitle("Deleted data can't be Undo!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });

    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent, false);
        return new myViewHolder(view);
    }
    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,feedback, rateCount;
        Button btnEdit, btnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.nametxt);
            feedback= (TextView)itemView.findViewById(R.id.feedbacktxt);
            rateCount =(TextView)itemView.findViewById(R.id.rateCounttxt);



            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete= (Button)itemView.findViewById(R.id.btnDelete);


        }
}




    }
