package economical.economical.economical.admin.model;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import economical.economical.economical.admin.Datalistener;
import economical.economical.economical.data.prodect_data;
import economical.economical.economical.data.super_prodect_data;
import economical.economical.economical.user.adapters.plastic_adapter;
import economical.economical.economical.user.u_show_product;

public class showproduct_model {
    private static showproduct_model model;
    private ArrayList<prodect_data> prodect_data;
    private ArrayList<super_prodect_data> super_data;
    private ArrayList<String> prodect_id;
    private static Fragment fragment;
    static Datalistener listener;
    private DatabaseReference database;
    public static showproduct_model initialize(Fragment frag) {
        fragment=frag;
        if (model == null)
        {
            model=new showproduct_model();
        }
        listener=(Datalistener) fragment;
        return model;
    }
    private void get_firebase_data(String type)
    {
        prodect_data=new ArrayList<prodect_data>();
        super_data=new ArrayList<super_prodect_data>();
        prodect_id=new ArrayList<String>();
        Log.d("act","walid salah");
         database= FirebaseDatabase.getInstance().getReference();
        database.child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prodect_data.clear();
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    String name=snap.child("name").getValue().toString();
                    String price=snap.child("price").getValue().toString();
                    String description=snap.child("description").getValue().toString();
                    String type_p=snap.child("type").getValue().toString();
                    String id=snap.child("id").getValue().toString();
                    prodect_id.add(id);
                    super_data.add(new super_prodect_data(name, description,type_p,price,id));
                    Log.d("act",name);
                }
            get_images(prodect_id,type);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void get_images(ArrayList<String> id,String type)
    {
        for (int i=0;i<id.size();i++)
        {
            int y= i;
            database.child(type).child(id.get(i)).child("images").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ArrayList<String>  images=new ArrayList<String>();
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String i = snap.child("image").getValue().toString();
                            images.add(i);
                        }
                        prodect_data.add(new prodect_data(super_data.get(y).getName(), super_data.get(y).getDescription()
                                , super_data.get(y).getType(), super_data.get(y).getPrice(), super_data.get(y).getId(), images));
                        listener.ongetdata();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public MutableLiveData<ArrayList<prodect_data>> getdata(String type)
    {
        get_firebase_data(type);
        MutableLiveData<ArrayList<prodect_data>> mute=new MutableLiveData<ArrayList<prodect_data>>();
        mute.setValue(prodect_data);
        return mute;
    }

}
