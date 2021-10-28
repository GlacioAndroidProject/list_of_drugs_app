package com.frsarker.medicineApp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frsarker.medicineApp.MedicineDetail;
import com.frsarker.medicineApp.R;
import com.frsarker.medicineApp.data.Medicine_object;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Medicine_object> medicine_objects;
    Context context;
    public MedicineAdapter(Context context, ArrayList<Medicine_object> medicine_objects){
        this.context = context;
        this.medicine_objects = medicine_objects;
    }
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void SetMedicineObject(ArrayList<Medicine_object> medicine_objects){
        this.medicine_objects = medicine_objects;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_row_item, parent, false);
        return new MedicineAdapter.ItemViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MedicineAdapter.ItemViewHolder) {
            populateItemRows((MedicineAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return medicine_objects == null ? 0 : medicine_objects.size();
    }
    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView medicine_name, medicine_type, medicine_content, medicine_uses;
        LinearLayout ln_row_item;
        ImageView medicine_image;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            medicine_name = itemView.findViewById(R.id.medicine_name);
            medicine_type = itemView.findViewById(R.id.medicine_type);
            medicine_content = itemView.findViewById(R.id.medicine_content);
            medicine_uses = itemView.findViewById(R.id.medicine_uses);
            medicine_image = itemView.findViewById(R.id.medicine_image);
            ln_row_item = itemView.findViewById(R.id.ln_row_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });

        }
    }
    private void populateItemRows(MedicineAdapter.ItemViewHolder viewHolder, final int position) {
        final Medicine_object medicine_object = medicine_objects.get(position);

        viewHolder.ln_row_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetailPage = new Intent(context, MedicineDetail.class);
                gotoDetailPage.putExtra("medicine_object",medicine_object);
                context.startActivity(gotoDetailPage);
            }
        });
        viewHolder.medicine_name.setText(medicine_object.getName());
        //viewHolder.medicine_name.setSelected(true);
        viewHolder.medicine_content.setText(medicine_object.getContent());
        viewHolder.medicine_content.setSelected(true);
        viewHolder.medicine_type.setText(medicine_object.getType());
        viewHolder.medicine_uses.setText(medicine_object.getUses());
        viewHolder.medicine_uses.setSelected(true);


        // get input stream
        InputStream ims = null;
        try {
            System.out.println("Imageurl: "+ medicine_object.getImageUrl());
            if(medicine_object.getImageUrl()==null)
            {
                viewHolder.medicine_image.setImageResource(R.drawable.missing_image);
                return;
            }
            ims = context.getAssets().open(medicine_object.getImageUrl());
            // load image as Drawable
            Drawable image = Drawable.createFromStream(ims, null);
            // set image to ImageView
            viewHolder.medicine_image.setImageDrawable(image);
            ims .close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
