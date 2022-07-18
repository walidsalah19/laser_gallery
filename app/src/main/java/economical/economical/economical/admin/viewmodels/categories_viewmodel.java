package economical.economical.economical.admin.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import economical.economical.economical.admin.model.categories_model;
import economical.economical.economical.data.category_data;

public class categories_viewmodel extends ViewModel {
    MutableLiveData<ArrayList<category_data>> categories ;
    categories_model model;
    public void intialize(Fragment context) {
        if (categories != null)
        {
            return;
        }
        model=categories_model.intialize(context);
        categories=model.gatlivedata();
    }
    public LiveData<ArrayList<category_data>> getCategories() {
        if (categories == null)
        {
            categories=model.gatlivedata();
        }
        return categories;
    }
}
