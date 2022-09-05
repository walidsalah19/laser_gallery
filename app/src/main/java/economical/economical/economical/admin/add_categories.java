package economical.economical.economical.admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import economical.economical.economical.Datalistener;
import economical.economical.economical.R;
import economical.economical.economical.admin.viewmodels.add_category_viewmodel;
import economical.economical.economical.data.category_data;

public class add_categories extends Fragment implements Datalistener {


    private ImageView image;
    private EditText name;
    private TextView add_image;
    private Button add;
    private String image_str="";
    add_category_viewmodel model;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_categories, container, false);
        initialize(v);
        model=new ViewModelProvider(getActivity()).get(add_category_viewmodel.class);
        select_image();
        add_category(v);
        return v;
    }
    private void initialize(View v)
    {
       image= v.findViewById(R.id.add_category_image);
        name= v.findViewById(R.id.add_category_name);
        add= v.findViewById(R.id.add_category);
        add_image=v.findViewById(R.id.add_image);
    }
    private void select_image()
    {
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 || resultCode == Activity.RESULT_OK || data != null || data.getData() != null) {
                Log.d("imag","popopop");
                getimage(data.getData());
            }
        }catch (Exception ex)
        {
        }

    }
    private void getimage(Uri uri)
    {
       model.initial(add_categories.this,uri);
    }
    private void add_category(View v)
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().equals(""))
                {
                    name.setError("ادخل اسم الفئة");
                }
                else if(image_str.equals(""))
                {
                    Toast.makeText(getActivity(),"اختر صوره او انتظر تحميل الصوره",Toast.LENGTH_LONG).show();
                }
                else
                {
                    model.intial_categories(add_categories.this,new category_data(name.getText().toString(),image_str));
                }
            }
        });
    }

    @Override
    public void ongetdata() {
        model.getImage().observe(getActivity(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> arrayList) {
              //  Log.d("imag",model.getImage().getValue().get(0));
                image_str=model.getImage().getValue().get(0);
                Glide.with(getActivity()).load(model.getImage().getValue().get(0)).into(image);
            }
        });

    }

    @Override
    public void onadddata() {

    }
}