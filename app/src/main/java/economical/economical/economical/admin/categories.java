package economical.economical.economical.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.admin.adapters.categories_adapter;
import economical.economical.economical.admin.viewmodels.categories_viewmodel;
import economical.economical.economical.data.category_data;

public class categories extends Fragment implements Datalistener {
    private RecyclerView recyclerView;
    private categories_viewmodel viewmodel;
    private categories_adapter adapter;
    private ExtendedFloatingActionButton newcategory;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_catigories, container, false);
         recyclerview(v);
        getdata();
        add_newcategory(v);
        return v;
    }
    private void recyclerview(View v)
    {
        recyclerView=v.findViewById(R.id.admin_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    private void getdata()
    {
        viewmodel= new ViewModelProvider(getActivity()).get(categories_viewmodel.class);
        viewmodel.intialize(categories.this);
        adapter=new categories_adapter(viewmodel.getCategories().getValue(),categories.this,0);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ongetdata() {
        viewmodel.getCategories().observe(getActivity(), new Observer<ArrayList<category_data>>() {
            @Override
            public void onChanged(ArrayList<category_data> category_data) {
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void add_newcategory(View v)
    {
        newcategory=v.findViewById(R.id.add_new_category);
        newcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_framelayout,new add_categories()).addToBackStack(null).commitAllowingStateLoss();
            }
        });
    }
}