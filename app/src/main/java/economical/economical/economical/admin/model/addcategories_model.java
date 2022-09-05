package economical.economical.economical.admin.model;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.UUID;

import economical.economical.economical.Datalistener;
import economical.economical.economical.data.category_data;

public class addcategories_model {
    private DatabaseReference database;
    private static  Fragment fragment;
    static addcategories_model model;
    ArrayList<String> image=new ArrayList<>();
    static Datalistener listener;
    public static addcategories_model initialize(Fragment fragmet)
    {
        if (model==null)
        {
            model=new addcategories_model();
        }
        fragment=fragmet;
        listener=(Datalistener) fragment;
        return  model;
    }
    private boolean found=false;
    public void check_data(category_data data)
    {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String name=snap.child("name").getValue().toString();
                        if (name.equals(data.getName()))
                        {
                            found=true;
                            break;
                        }
                    }
                    if (found)
                    {
                        Toast.makeText(fragment.getActivity(), "لا يمكنك اضافة هذه الفئه لانها موجودة بالفعل", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        addcategories(data);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addcategories(category_data data)
    {
        database=FirebaseDatabase.getInstance().getReference();
        database.child("categories").push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                  Toast.makeText(fragment.getActivity(), "تم اضافة فئه جديدة", Toast.LENGTH_SHORT).show();
                  fragment.getActivity().onBackPressed();
              }
            }
        });
    }
    public void  upload_image(Uri imageUri) {
        String image_id = UUID.randomUUID().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images_users/").child(image_id);
        StorageTask task = reference.putFile(imageUri);
        task.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri i = task.getResult();
                    image.add(i.toString());
                } else {
                }
                listener.ongetdata();
            }
        });
    }
    public MutableLiveData<ArrayList<String>> getimage(Uri uri)
    {
        upload_image(uri);
         MutableLiveData<ArrayList<String>> livedata=new MutableLiveData<>();
         livedata.setValue(image);
         return livedata;
    }

}
