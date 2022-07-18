package economical.economical.economical.user.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.admin.update_product;
import economical.economical.economical.data.prodect_data;
import economical.economical.economical.multiple_imageview.imageview;
import economical.economical.economical.user.product_details;

public class plastic_adapter extends RecyclerView.Adapter<plastic_adapter.help>{
  ArrayList<prodect_data> arrayList ;
    Fragment fragment;
    int type;
    public plastic_adapter(ArrayList<prodect_data> arrayList, Fragment fragment, int type) {
        this.arrayList = arrayList;
        this.fragment = fragment;
        this.type=type;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_content, parent, false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
        imageview image=new imageview(fragment,holder.recyclerview,arrayList.get(position).getImages());
        holder.price.setText(arrayList.get(position).getPrice());
        holder.name.setText(arrayList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putString("name",arrayList.get(position).getName());
                b.putString("price",arrayList.get(position).getPrice());
                b.putString("des",arrayList.get(position).getDescription());
                b.putString("type",arrayList.get(position).getType());
                b.putString("id",arrayList.get(position).getId());
                b.putStringArrayList("image",arrayList.get(position).getImages());
                product_details pro=new product_details();
                pro.setArguments(b);
                if (type==0)
                {
                    update_product u=new update_product();
                    u.setArguments(b);
                    fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_framelayout, u).addToBackStack(null).commitAllowingStateLoss();
                }
                else if (type==1){
                    fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.user_framelayout, pro).addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        TextView name,price;
        RecyclerView recyclerview;
        public help(@NonNull View itemView) {
            super(itemView);
            recyclerview=(RecyclerView)itemView.findViewById(R.id.multipleImageview);
            name=(TextView)itemView.findViewById(R.id.m_report_recycler_name);
            price=(TextView)itemView.findViewById(R.id.plactic_price);
        }
    }
}
