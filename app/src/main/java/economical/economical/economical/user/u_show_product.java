package economical.economical.economical.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.admin.Datalistener;
import economical.economical.economical.admin.viewmodels.shoeprodect_ciewmodel;
import economical.economical.economical.data.prodect_data;
import economical.economical.economical.user.adapters.plastic_adapter;
public class u_show_product extends Fragment implements Datalistener {

    private RecyclerView recyclerView;
    private ArrayList<prodect_data> arrayList;
    plastic_adapter adapter;
    private shoeprodect_ciewmodel viewmodel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_plastic, container, false);

        viewmodel=new ViewModelProvider(getActivity()).get(shoeprodect_ciewmodel.class);
        recyclerView_method(v);
        searchview(v);
        return v;
    }
    private String gettype()
    {
       return getArguments().getString("type");
    }
    private void recyclerView_method(View view) {
        recyclerView=view.findViewById(R.id.plastic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewmodel.initial(u_show_product.this,gettype());
        adapter=new plastic_adapter(viewmodel.getdata().getValue(), u_show_product.this,1);
        recyclerView.setAdapter(adapter);
    }
    private void searchview(View v)
    {
        SearchView search=(SearchView)v.findViewById(R.id.search_view);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                get_device_searched(s);
                return false;
            }
        });
    }
    private void get_device_searched(String s) {
        ArrayList<prodect_data>arr=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++) {
            if(arrayList.get(i).getName().contains(s))
            {
                arr.add(arrayList.get(i));
            }
        }
        if(arr.size()==0)
        {
            plastic_adapter adapter=new plastic_adapter(arr, u_show_product.this,1);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else{
            plastic_adapter adapter=new plastic_adapter(arr, u_show_product.this,1);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ongetdata() {
       viewmodel.getdata().observe(getActivity(), new Observer<ArrayList<prodect_data>>() {
           @Override
           public void onChanged(ArrayList<prodect_data> prodect_data) {
               adapter.notifyDataSetChanged();
               arrayList=new ArrayList<prodect_data>();
               arrayList.addAll(viewmodel.getdata().getValue());
           }
       });
    }

    @Override
    public void onadddata() {

    }
}