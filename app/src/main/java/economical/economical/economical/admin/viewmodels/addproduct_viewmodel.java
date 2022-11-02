package economical.economical.economical.admin.viewmodels;

import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import economical.economical.economical.admin.model.addproduct_model;
import economical.economical.economical.data.prodect_data;

public class addproduct_viewmodel extends ViewModel {
    MutableLiveData<ArrayList<Boolean>> products;
    MutableLiveData<ArrayList<String>> images ;
    addproduct_model model;
    String type , id;
    prodect_data data;
    ArrayList<Uri> arr;
    public void initialize(Fragment fragment) {
        if (products != null) {
            return;
        }
        model=addproduct_model.intialize(fragment);
    }
    public void send_product_firebase(String type, String id, prodect_data data)
    {
        Log.d("this1","fgh1");
        this.type = type;
        this.id = id;
        this.data = data;
        products=model.checkdata(type,id,data);
    }
    public void send_image(ArrayList<Uri> arr)
    {
        this.arr = arr;
        images=model.getimage(arr);
    }
    public LiveData<ArrayList<Boolean>> checkdata()
    {
        if (products==null)
        {
            products=model.checkdata(type,id,data);
        }
        return products;
    }
    public LiveData<ArrayList<String>> getimages()
    {
        if (images==null)
        {
            images=model.getimage(arr);
        }
        return images;
    }
    public void delete_product(Fragment fragment,String type, String id)
    {
        model.delete_product(fragment,type,id);
    }
}
