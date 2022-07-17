package economical.economical.economical.admin.model;

import android.content.Context;
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
import economical.economical.economical.data.category_data;

public class categories_model {
    private static categories_model model;
    private ArrayList<category_data> categoryData;
    static Fragment context;
    static Datalistener datalistener;
    public static categories_model intialize(Fragment Context)
    {
        context = Context;
        if (model == null)
        {
            model = new categories_model();
        }
        datalistener=(Datalistener) context;
        return model;
    }
    private void get_categories()
    {
        categoryData=new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists())
               {
                   categoryData.clear();
                   for (DataSnapshot snap:snapshot.getChildren())
                   {
                       String name=snap.child("name").getValue().toString();
                       String image=snap.child("image").getValue().toString();
                       Log.d("act",name);
                       categoryData.add(new category_data(name,image));
                   }
                   datalistener.ongetdata();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public MutableLiveData<ArrayList<category_data>> gatlivedata()
    {
        get_categories();
        MutableLiveData<ArrayList<category_data>> data=new MutableLiveData<ArrayList<category_data>>();
        data.setValue(categoryData);
        return data;
    }
}
