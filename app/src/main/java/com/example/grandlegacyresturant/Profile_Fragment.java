package com.example.grandlegacyresturant;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Profile_Fragment extends Fragment {
    TextView txtUname,txtFname,txtLname,txtDob,txtEmail;
    ImageView profileImageView;
    Uri imageUri;

    ImageButton profileUpdate_btn;
    Button deleteAccount;
    Button userLogout;
    Button editProfile;
    Button editpass;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference dbReference;
    StorageReference storageReference;



    public Profile_Fragment() {
        // Required empty public constructor

    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile_, container, false);

        dbReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();




        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });


        txtUname = (TextView) v.findViewById(R.id.display_name);
        txtFname = (TextView) v.findViewById(R.id.display_fname);
        txtLname = (TextView) v.findViewById(R.id.display_lname);
        txtDob = (TextView) v.findViewById(R.id.display_dob);
        txtEmail = (TextView) v.findViewById(R.id.display_email);
        deleteAccount = (Button) v.findViewById(R.id.delete_btn);
        userLogout = (Button) v.findViewById(R.id.logout_btn);
        editProfile =(Button) v.findViewById(R.id.update_btn);
        profileUpdate_btn = (ImageButton) v.findViewById(R.id.upload_image_btn);
        profileImageView = (ImageView) v.findViewById(R.id.proimage);
        editpass =(Button) v.findViewById(R.id.forgetpassbtn);

        txtUname.setText(GlobalVal.currentUser.getUserName());
        txtFname.setText(GlobalVal.currentUser.getFname());
        txtLname.setText(GlobalVal.currentUser.getLname());
        txtDob.setText(GlobalVal.currentUser.getPno());
        txtEmail.setText(GlobalVal.currentUser.getEmail());

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        editpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),FogetPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Are you sure ?");
                dialog.setMessage("Deleting this account will result in completely removing your account from the system and you won't be able to access the app");

                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Account Deleted",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getActivity(),"Invalid User Name Or Password",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),profileUpdateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profileUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);

            }
        });

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                signOutUser();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //upload image to firebase storage
        StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });
                Toast.makeText(getActivity(),"Image Uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(),"Image Upload Fail",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void signOutUser() {
        Toast.makeText(getActivity(),"Logout success",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
