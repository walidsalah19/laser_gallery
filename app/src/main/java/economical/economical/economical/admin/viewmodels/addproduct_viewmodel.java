package economical.economical.economical.admin.viewmodels;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import economical.economical.economical.admin.model.addproduct_model;
import economical.economical.economical.data.prodect_data;

public class addproduct_viewmodel extends ViewModel {
    MutableLiveData<ArrayList<Boolean>> products ;
    MutableLiveData<ArrayList<String>> images ;
    addproduct_model model;
    public void initialize(Fragment fragment) {
        if (products != null) {
            return;
        }
        model=addproduct_model.intialize(fragment);
    }
    public void send_product_firebase(String type, String id, prodect_data data)
    {
        products=model.checkdata(type,id,data);
    }
    public void send_image(ArrayList<Uri> arr)
    {
        images=model.getimage(arr);
    }
    public LiveData<ArrayList<Boolean>> checkdata()
    {
        return products;
    }
    public LiveData<ArrayList<String>> getimages()
    {
        return images;
    }
}
