package economical.economical.economical.admin;

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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.admin.viewmodels.shoeprodect_ciewmodel;
import economical.economical.economical.data.prodect_data;
import economical.economical.economical.user.adapters.plastic_adapter;
import economical.economical.economical.user.u_show_product;

public class show_product extends Fragment implements Datalistener{
    private RecyclerView recyclerView;
    private ArrayList<prodect_data> arrayList;
    private ArrayList<String> images;
    plastic_adapter adapter;
    private shoeprodect_ciewmodel viewmodel;
    private ExtendedFloatingActionButton newcategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_product, container, false);
        viewmodel=new ViewModelProvider(getActivity()).get(shoeprodect_ciewmodel.class);
        viewmodel.initial(show_product.this,gettype());
        recyclerView_method(v);
        searchview(v);
        add_new_product(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //arrayList.clear();
    }

    private String gettype()
    {
        return getArguments().getString("type");
    }
    private void recyclerView_method(View view) {
        recyclerView=view.findViewById(R.id.a_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    private void searchview(View v)
    {
        SearchView search=(SearchView)v.findViewById(R.id.a_search_view);
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
        if (arrayList==null)
        {
            arrayList=new ArrayList<>();
        }
        for(int i=0;i<arrayList.size();i++) {
            if(arrayList.get(i).getName().contains(s))
            {
                arr.add(arrayList.get(i));
            }
        }
        if(arr.size()==0)
        {
            plastic_adapter adapter=new plastic_adapter(arr, show_product.this,0);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else{
            plastic_adapter adapter=new plastic_adapter(arr, show_product.this,0);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    private void add_new_product(View v)
    {
        newcategory=v.findViewById(R.id.add_new_product);
        newcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putString("type",gettype());
                add_new_product p=new add_new_product();
                p.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_framelayout,p).addToBackStack(null).commitAllowingStateLoss();
            }
        });
    }
    @Override
    public void ongetdata() {
        viewmodel.getdata().observe(getActivity(), new Observer<ArrayList<prodect_data>>() {
            @Override
            public void onChanged(ArrayList<prodect_data> prodect_data) {
                arrayList=new ArrayList<prodect_data>();
                for(int i = 0; i <viewmodel.getdata().getValue().size();i++)
                {
                    arrayList.add(viewmodel.getdata().getValue().get(i));
                }
                adapter=new plastic_adapter(viewmodel.getdata().getValue(), show_product.this,0);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onadddata() {

    }
}