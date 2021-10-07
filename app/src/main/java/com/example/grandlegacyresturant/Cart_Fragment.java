package com.example.grandlegacyresturant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grandlegacyresturant.Holder.CartViweHolder;
import com.example.grandlegacyresturant.Model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart_Fragment} factory method to
 * create an instance of this fragment.
 */
public class Cart_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button chkout_btn;
    private TextView net_pricetxt,textMsg;
    private int totalPrice =0;


    public Cart_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_cart_, container, false);

        recyclerView = v.findViewById(R.id.cart_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        chkout_btn =(Button) v.findViewById(R.id.chkoutbtn);
        net_pricetxt = (TextView) v.findViewById(R.id.net_price);


        chkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CheckoutOrderActivity.class);
                intent.putExtra("Total Price",String.valueOf(totalPrice));
                startActivity(intent);
            }
        });


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(cartListRef.child("User View")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Foods"),CartModel.class)
                        .build();

        FirebaseRecyclerAdapter<CartModel, CartViweHolder> adapter= new FirebaseRecyclerAdapter<CartModel, CartViweHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull CartViweHolder holder, int position, @NonNull @NotNull CartModel model)
            {
                holder.TxtQuantity.setText(model.getQuantity());
                holder.txtFoodName.setText(model.getName());
                holder.txtPrice.setText("RS."+model.getPrice()+".00");




                int oneTypeProductPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());


                totalPrice = totalPrice + oneTypeProductPrice;
                net_pricetxt.setText("Total Price = RS." + String.valueOf(totalPrice)+".00");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence option[] = new CharSequence[]{
                                "Edit",
                                "Delete"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Cart Options");

                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    Intent intent = new Intent(getContext(),FoodDetails.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                                if(which== 1){
                                    cartListRef.child("User View")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Foods")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(getContext(), "Item removed successful", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(getContext(),profileActivity.class);
                                                        startActivity(i);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();

                    }
                });

            }

            @NonNull
            @NotNull
            @Override
            public CartViweHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViweHolder holder = new CartViweHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    private  void checkOrderSate(){

    }
}