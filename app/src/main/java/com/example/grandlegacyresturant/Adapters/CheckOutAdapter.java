package com.example.grandlegacyresturant.Adapters;

import android.app.AlertDialog;
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

import com.example.grandlegacyresturant.CheckOutActivity;
import com.example.grandlegacyresturant.Model.CheckOutModel;
import com.example.grandlegacyresturant.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CheckOutAdapter extends FirebaseRecyclerAdapter <CheckOutModel, CheckOutAdapter.myViewHolder>{



    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CheckOutAdapter(@NonNull FirebaseRecyclerOptions<CheckOutModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CheckOutAdapter.myViewHolder holder, int position, @NonNull CheckOutModel model) {
        holder.name.setText(model.getName());
        holder.add1.setText(model.getAdd1());
        holder.add2.setText(model.getAdd2());
        holder.city.setText(model.getCity());
        holder.district.setText(model.getDistrict());
        holder.province.setText(model.getProvince());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_address_popup))
                        .setExpanded(true,1550)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.upName);
                EditText add1 = view.findViewById(R.id.upAdd1);
                EditText add2 = view.findViewById(R.id.upAdd2);
                EditText city = view.findViewById(R.id.upCity);
                EditText district = view.findViewById(R.id.upDistrict);
                EditText province = view.findViewById(R.id.upProvince);

                Button btnUpdate = view.findViewById(R.id.RbtnUpdate);


                name.setText(model.getName());
                add1.setText(model.getAdd1());
                add2.setText(model.getAdd2());
                city.setText(model.getCity());
                district.setText(model.getDistrict());
                province.setText(model.getProvince());



                dialogPlus.show();


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("add1",add1.getText().toString());
                        map.put("add2",add2.getText().toString());
                        map.put("city",city.getText().toString());
                        map.put("district",district.getText().toString());
                        map.put("province",province.getText().toString());



                        FirebaseDatabase.getInstance().getReference().child("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
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
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

            }
        });

    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_address,parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,add1,add2,city,district,province;

        Button btnEdit,btnDelete;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.nametxt);
            add1 = (TextView)itemView.findViewById(R.id.add1txt);
            add2 = (TextView)itemView.findViewById(R.id.add2txt);
            city = (TextView)itemView.findViewById(R.id.citytxt);
            district = (TextView)itemView.findViewById(R.id.districttxt);
            province = (TextView)itemView.findViewById(R.id.provincetxt);

            btnEdit = (Button)itemView.findViewById(R.id.RbtnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.RbtnDelete);




        }
    }

}
