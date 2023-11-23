package com.example.myapplication.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.AdminModelPharmacist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminAdapterPharmacist extends RecyclerView.Adapter<AdminAdapterPharmacist.HolderPharmacist>{

    private Context mContext;
    public ArrayList<AdminModelPharmacist> mPharmacistList;

    public AdminAdapterPharmacist(Context context, ArrayList<AdminModelPharmacist> pharmacistList) {
        this.mContext = context;
        this.mPharmacistList = pharmacistList;
    }

    @NonNull
    @Override
    public HolderPharmacist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_pharmacist_list, parent, false);
        return new AdminAdapterPharmacist.HolderPharmacist(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPharmacist holder, int position) {
        //get data
        AdminModelPharmacist adminModelPharmacist = mPharmacistList.get(position);
        String id = adminModelPharmacist.getPharmacistId();
        String uid = adminModelPharmacist.getUid();
        String name = adminModelPharmacist.getName();
        String email = adminModelPharmacist.getEmail();
        String age = adminModelPharmacist.getAge();
        String accountType = adminModelPharmacist.getAccountType();
        String timestamp = adminModelPharmacist.getTimestamp();

        //set data
        holder.nameTv.setText(name);
        holder.emailTv.setText(email);
        holder.ageTv.setText(age);
        holder.accountTypeTv.setText(accountType);

        // Set an OnClickListener for the delete button
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show delete confirm dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this pharmacist " + name + " ?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //delete
                                int position = holder.getAdapterPosition();
                                deletePharmacist(uid, position); //id is the pharmacist id
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //cancel, dismiss dialog
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
    private void deletePharmacist(String uid, int position) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        reference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Pharmacist deleted successfully
                        Toast.makeText(mContext, "Pharmacist Deleted Successfully", Toast.LENGTH_SHORT).show();
                        // Remove the pharmacist from the list and notify the adapter
                        mPharmacistList.remove(position); // Remove the pharmacist entry from the list
                        notifyDataSetChanged(); // Notify the adapter about the data set change
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Deletion failed
                        Toast.makeText(mContext, "Failed to delete pharmacist: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public int getItemCount() {
        return mPharmacistList.size();
    }

    class HolderPharmacist extends RecyclerView.ViewHolder{

        private TextView nameTv, emailTv, ageTv, accountTypeTv;
        private Button deleteBtn;

        public HolderPharmacist(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.nameTv);
            emailTv = itemView.findViewById(R.id.emailTv);
            ageTv = itemView.findViewById(R.id.ageTv);
            accountTypeTv = itemView.findViewById(R.id.accountTypeTv);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
