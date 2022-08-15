package economical.economical.economical.admin.viewmodels;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import economical.economical.economical.admin.model.addcategories_model;
import economical.economical.economical.admin.model.categories_model;
import economical.economical.economical.data.category_data;

public class add_category_viewmodel extends ViewModel {
    MutableLiveData<ArrayList<String>> image ;
    addcategories_model model;
    Uri uri;
    public void initial(Fragment fragment, Uri uri)
    {
        this.uri = uri;
        if (image != null)
        {
            return;
        }
        model=addcategories_model.initialize(fragment);
        image=model.getimage(uri);
    }
    public void intial_categories(Fragment fragment,category_data data)
    {
        model=addcategories_model.initialize(fragment);
        model.check_data(data);
    }
    public LiveData<ArrayList<String>>getImage()
    {
        if (image==null)
        {
            image=model.getimage(uri);
        }
        return image;
    }

}
