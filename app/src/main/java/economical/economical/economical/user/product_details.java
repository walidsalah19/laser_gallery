package economical.economical.economical.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.multiple_imageview.imageview;


public class product_details extends Fragment {


     TextView name,price,des,type;
     ArrayList<String> image;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_product_details, container, false);

       initialization(v);
       getdata();
        bay(v);
        return v;
    }
    private void bay(View v)
    {
        TextView bay=(TextView) v.findViewById(R.id.bay_product);
         bay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
              send_to_whatsapp();
             }
         });
    }
    private void send_to_whatsapp()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+201555723297" +"&text="+"الاسم  "  +name.getText().toString()+"\n"+"النوع  "+type.getText().toString()+"\n"+
       "السعر  " +price.getText().toString()));
        startActivity(intent);
    }
    private void initialization(View v)
    {
        name=v.findViewById(R.id.product_name);
        des=v.findViewById(R.id.product_des);
        price=v.findViewById(R.id.product_price);
        type=v.findViewById(R.id.product_type);
         recyclerView=(RecyclerView) v.findViewById(R.id.multipleImageview);

    }
    private void getdata()
    {
        String na=getArguments().getString("name");
        String pr=getArguments().getString("price");
        String de=getArguments().getString("des");
        String ty=getArguments().getString("type");
        image=getArguments().getStringArrayList("image");
        name.setText(na);
        price.setText(pr);
        des.setText(de);
        type.setText(ty);
        imageview multi=new imageview(this,recyclerView,image);

    }
}