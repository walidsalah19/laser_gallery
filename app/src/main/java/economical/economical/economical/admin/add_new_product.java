package economical.economical.economical.admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.admin.viewmodels.addproduct_viewmodel;
import economical.economical.economical.multiple_imageview.imageview;

public class add_new_product extends Fragment implements Datalistener{

     private TextView add_image;
     private RecyclerView multiimage;
     private EditText name,type,price,description;
     private Button add_data;
     private ArrayList<Uri> images;
    private ArrayList<String> images_str;
    private addproduct_viewmodel viewmodel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_new_product, container, false);
        viewmodel=new ViewModelProvider(getActivity()).get(addproduct_viewmodel.class);
        viewmodel.initialize(add_new_product.this);
        initialize(v);
        select_image();
        return v;
    }
    private void initialize(View v)
    {
        add_image=v.findViewById(R.id.add_p_image);
        name=v.findViewById(R.id.add_product_name);
        type=v.findViewById(R.id.add_product_type);
        price=v.findViewById(R.id.add_product_price);
        description=v.findViewById(R.id.add_product_des);
        add_data=v.findViewById(R.id.add_new_pro);
        multiimage=v.findViewById(R.id.multipleImageview);
        images=new ArrayList<Uri>();
        images_str=new ArrayList<String>();
    }
    private void add_data()
    {
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (images_str.size()==0)
                {
                    Toast.makeText(getActivity(), " اختر الصور او انتظر نحمل الصور ", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(name.getText().toString()))
                {
                    name.setError("اضف اسم المنتج");
                }
                else if (TextUtils.isEmpty(price.getText().toString()))
                {
                    price.setError("اضف اسم المنتج");
                }
                else if (TextUtils.isEmpty(name.getText().toString()))
                {
                    name.setError("اضف اسم المنتج");
                }
            }
        });
    }
    private void select_image()
    {

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for(int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        images.add(imageUri);
                    }
                    viewmodel.send_image(images);
                }
            } else if(data.getData() != null) {
                String imagePath = data.getData().getPath();
            }
        }
    }

    @Override
    public void ongetdata() {
        viewmodel.getimages().observe(getActivity(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> arrayList) {
                images_str.addAll(viewmodel.getimages().getValue());
                imageview image=new imageview(add_new_product.this,multiimage,viewmodel.getimages().getValue());
            }
        });
    }
}