package economical.economical.economical.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

import economical.economical.economical.Datalistener;
import economical.economical.economical.R;
import economical.economical.economical.adapters.categories_adapter;
import economical.economical.economical.adapters.discount_adapter;
import economical.economical.economical.admin.viewmodels.categories_viewmodel;
import economical.economical.economical.admin.viewmodels.shoeprodect_ciewmodel;
import economical.economical.economical.data.category_data;
import economical.economical.economical.data.prodect_data;
public class categories extends Fragment implements Datalistener {
    private RecyclerView recyclerView,recycler_discount;
    private categories_viewmodel viewmodel;
    private categories_adapter adapter;
    private ExtendedFloatingActionButton newcategory;
    private TextView add_discount;
    private discount_adapter adapter_dis;
    private shoeprodect_ciewmodel viewmodel_dis;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_catigories, container, false);
        viewmodel= new ViewModelProvider(getActivity()).get(categories_viewmodel.class);
        viewmodel.intialize(categories.this);
         recyclerview(v);
        getdata();
        add_newcategory(v);
        add_discount(v);
        getdiscount();
        return v;
    }
    private void recyclerview(View v)
    {
        recyclerView=v.findViewById(R.id.admin_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_discount=v.findViewById(R.id.user_discounts);
        recycler_discount.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
    }
    private void getdata()
    {

        adapter=new categories_adapter(viewmodel.getCategories().getValue(),categories.this,0);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void getdiscount()
    {
        viewmodel_dis=new ViewModelProvider(getActivity()).get(shoeprodect_ciewmodel.class);
        viewmodel_dis.initial(categories.this,"العروض");
        adapter_dis=new discount_adapter(viewmodel_dis.getdata().getValue(),categories.this,0);
        recycler_discount.setAdapter(adapter_dis);

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

    @Override
    public void onadddata() {
       viewmodel_dis.getdata().observe(getActivity(), new Observer<ArrayList<prodect_data>>() {
           @Override
           public void onChanged(ArrayList<prodect_data> prodect_data) {
               adapter_dis.notifyDataSetChanged();
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
    private void add_discount(View v)
    {
        add_discount=v.findViewById(R.id.add_discount);
        add_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_framelayout,new add_discount()).addToBackStack(null).commitAllowingStateLoss();
            }
        });
    }
}