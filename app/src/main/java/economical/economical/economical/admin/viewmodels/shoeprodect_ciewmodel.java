package economical.economical.economical.admin.viewmodels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import economical.economical.economical.admin.model.showproduct_model;
import economical.economical.economical.data.prodect_data;

public class shoeprodect_ciewmodel extends ViewModel {
    MutableLiveData<ArrayList<prodect_data>> mute;
    showproduct_model model;
    public void initial(Fragment fragment,String type)
    {
        if (mute != null)
        {
            return;
        }
        model=showproduct_model.initialize(fragment);
        mute=model.getdata(type);
    }
    public LiveData<ArrayList<prodect_data>> getdata()
    {
        return mute;
    }
}