package com.example.unknown.cureme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<MyModelClass> contactList;
    private List<MyModelClass> contactListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, blood_Group, sugar, heartBeat, blood_Pressure, temp;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            id = view.findViewById(R.id.id);
            heartBeat = view.findViewById(R.id.heartBeat);
            temp = itemView.findViewById(R.id.temp);
            blood_Group = itemView.findViewById(R.id.bloodgroup);
            blood_Pressure = itemView.findViewById(R.id.blood_Pressure);
            sugar = itemView.findViewById(R.id.sugar);
        }
    }


    public MyAdapter(Context context, List<MyModelClass> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cardview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MyModelClass contact = contactListFiltered.get(position);
        holder.name.setText("Name: " + contact.getName());
        holder.id.setText("Id: " + contact.getID());
        holder.heartBeat.setText("HeartBeat: " + contact.getHeartBeat());
        holder.temp.setText("Temperature: " + contact.getTemperature());
        holder.blood_Pressure.setText("Blood Pressure: " + contact.getBlood_Pressure());
        holder.blood_Group.setText("Blood Group: " + contact.getBlood_Group());
        holder.sugar.setText("Sugar" + contact.getSugar());

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<MyModelClass> filteredList = new ArrayList<>();
                    for (MyModelClass row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getID().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<MyModelClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}