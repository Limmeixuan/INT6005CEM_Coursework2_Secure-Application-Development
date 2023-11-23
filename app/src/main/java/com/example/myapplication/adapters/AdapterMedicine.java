package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.ModelMedicine;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterMedicine extends RecyclerView.Adapter<AdapterMedicine.HolderMedicine>{

    private Context mContext;
    public ArrayList<ModelMedicine> mMedicineList;

    public AdapterMedicine(Context context, ArrayList<ModelMedicine> medicineList) {
        this.mContext = context;
        this.mMedicineList = medicineList;
    }

    @NonNull
    @Override
    public HolderMedicine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_medicine, parent, false);
        return new HolderMedicine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMedicine holder, int position) {
        //get data
        ModelMedicine modelMedicine = mMedicineList.get(position);
        String id = modelMedicine.getMedicineId();
        String uid = modelMedicine.getUid();
        String title = modelMedicine.getMedicineTitle();
        String description = modelMedicine.getMedicineDescription();
        String timestamp = modelMedicine.getTimestamp();

        //set data
        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
    }

    @Override
    public int getItemCount() {
        return mMedicineList.size();
    }

    class HolderMedicine extends RecyclerView.ViewHolder{

        private TextView titleTv, descriptionTv;

        public HolderMedicine(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
        }
    }
}
