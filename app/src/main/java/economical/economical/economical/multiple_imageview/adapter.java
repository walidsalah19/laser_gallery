package economical.economical.economical.multiple_imageview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import economical.economical.economical.R;

public class adapter extends RecyclerView.Adapter<adapter.help>{
    ArrayList<String> ArrayList;
    Fragment fragment;

    public adapter(java.util.ArrayList<String> arrayList, Fragment fragment) {
        ArrayList = arrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_imageview_content,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
        Glide.with(fragment.getActivity()).load(ArrayList.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        ImageView image;
        public help(@NonNull View itemView) {
            super(itemView);
            image=(ImageView) itemView.findViewById(R.id.multiple_imageView);
        }
    }
}
