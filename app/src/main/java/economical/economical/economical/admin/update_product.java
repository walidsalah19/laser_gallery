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

import economical.economical.economical.Datalistener;
import economical.economical.economical.R;
import economical.economical.economical.admin.viewmodels.addproduct_viewmodel;
import economical.economical.economical.data.prodect_data;
import economical.economical.economical.multiple_imageview.imageview;

public class update_product extends Fragment implements Datalistener {
    private TextView add_image;
    private RecyclerView multiimage;
    private EditText name,type,price,description;
    private Button add_data,delete;
    private ArrayList<Uri> images;
    private ArrayList<String> images_str;
    private addproduct_viewmodel viewmodel;
    private String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_update_product, container, false);
        viewmodel=new ViewModelProvider(getActivity()).get(addproduct_viewmodel.class);
        viewmodel.initialize(update_product.this);
        initialize(v);
        get_data();
        add_data();
        select_image();
        delete_product();
        return v;
    }

    private void get_data()
    {
        String name_s=getArguments().getString("name");
        String price_s=getArguments().getString("price");
        String type_p=getArguments().getString("type");
        String des=getArguments().getString("des");
        id=getArguments().getString("id");
        images_str= getArguments().getStringArrayList("image");
        name.setText(name_s);
        price.setText(price_s);
        description.setText(des);
        type.setText(type_p);
        imageview image=new imageview(update_product.this,multiimage,images_str);
    }
    private void initialize(View v)
    {
        add_image=v.findViewById(R.id.up_p_image);
        name=v.findViewById(R.id.up_product_name);
        type=v.findViewById(R.id.up_product_type);
        price=v.findViewById(R.id.up_product_price);
        description=v.findViewById(R.id.up_product_des);
        add_data=v.findViewById(R.id.update_pro);
        delete=v.findViewById(R.id.delete_pro);
        multiimage=v.findViewById(R.id.multipleImageview);
        images=new ArrayList<Uri>();
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
                    price.setError("اضف سعر المنتج");
                }
                else if (TextUtils.isEmpty(description.getText().toString()))
                {
                    description.setError("اضف وصف المنتج");
                }
                else {
                    add_to_database();
                }
            }
        });
    }

    private void add_to_database() {
        prodect_data data=new prodect_data(name.getText().toString(),description.getText().toString(),type.getText().toString(),
                price.getText().toString(),id,images_str);
        viewmodel.send_product_firebase(type.getText().toString(),id,data);
    }
     private void delete_product()
     {
         delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 viewmodel.delete_product(update_product.this,type.getText().toString(),id);
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
                images_str.clear();
                images_str.addAll(viewmodel.getimages().getValue());
                imageview image=new imageview(update_product.this,multiimage,viewmodel.getimages().getValue());
            }
        });

    }

    @Override
    public void onadddata()
    {
        viewmodel.checkdata().observe(getActivity(), new Observer<ArrayList<Boolean>>() {
            @Override
            public void onChanged(ArrayList<Boolean> booleans) {
                Toast.makeText(getActivity(),"تم تعديل بيانات المنتج",Toast.LENGTH_LONG).show();
            }
        });
    }
}