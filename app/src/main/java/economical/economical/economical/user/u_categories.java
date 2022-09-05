package economical.economical.economical.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.Datalistener;
import economical.economical.economical.adapters.categories_adapter;
import economical.economical.economical.adapters.discount_adapter;
import economical.economical.economical.admin.categories;
import economical.economical.economical.admin.viewmodels.categories_viewmodel;
import economical.economical.economical.admin.viewmodels.shoeprodect_ciewmodel;
import economical.economical.economical.data.category_data;
import economical.economical.economical.data.prodect_data;

public class u_categories extends Fragment implements Datalistener {
    private RecyclerView recyclerView,recycler_discount;
    private categories_viewmodel viewmodel;
    private categories_adapter adapter;
    private TextView  text_discount;
    private shoeprodect_ciewmodel viewmodel_dis;
    private discount_adapter adapter_dis;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_u_categories, container, false);
        text_discount=v.findViewById(R.id.text_discount);
        recyclerview(v);
        getdata();
        get_discount();
        return v;
    }
    private void recyclerview(View v)
    {
        recyclerView=v.findViewById(R.id.user_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_discount=v.findViewById(R.id.user_discounts);
        recycler_discount.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        //mvvm model
        viewmodel= new ViewModelProvider(getActivity()).get(categories_viewmodel.class);
        viewmodel.intialize(u_categories.this);
    }
    private void getdata()
    {

        adapter=new categories_adapter(viewmodel.getCategories().getValue(),u_categories.this,1);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
     private void get_discount()
     {
         viewmodel_dis=new ViewModelProvider(getActivity()).get(shoeprodect_ciewmodel.class);
         viewmodel_dis.initial(u_categories.this,"العروض");
         adapter_dis=new discount_adapter(viewmodel_dis.getdata().getValue(),u_categories.this,1);
         recycler_discount.setAdapter(adapter_dis);
         if (viewmodel_dis.getdata().getValue().size()==0)
         {
             text_discount.setText(" لا توجد عروض العروض ");
         }
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
                if (viewmodel_dis.getdata().getValue().size()==0)
                {
                    text_discount.setText(" لا توجد عروض  ");
                }else {
                    text_discount.setText("العروض ");
                }

            }
        });
    }
}