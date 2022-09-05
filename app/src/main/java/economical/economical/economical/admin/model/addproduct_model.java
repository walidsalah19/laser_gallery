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
import economical.economical.economical.data.prodect_data;

public class addproduct_model {
    private static addproduct_model model;
    private ArrayList<Boolean> arrayList;
    private ArrayList<String> images;
    private static Datalistener listener;
    private static Fragment fragment;
    private static DatabaseReference database;
    public static addproduct_model intialize(Fragment frag)
    {
        fragment=frag;
        if (model==null)
        {
            model=new addproduct_model();
        }
        listener=(Datalistener) fragment;
         database= FirebaseDatabase.getInstance().getReference();

        return model;
    }
    private boolean found=false;
    private void check_founddata(String type, String id, prodect_data data)
    {
        database= FirebaseDatabase.getInstance().getReference();
        database.child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()) {
                    String name = snap.child("name").getValue().toString();
                    if (name.equals(data.getName())) {
                        found = true;
                        //Toast.makeText(fragment.getActivity(), "لا يمكنك اضافه هذا المنتج لانه موجود بالفعل", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if (!found)
                {
                    add_data(type,id,data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void add_data(String type, String id, prodect_data data)
    {
        arrayList=new ArrayList<Boolean>();
        database.child(type).child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful())
                  {
                      arrayList.add(true);
                      listener.onadddata();
                  }
            }
        });
    }
    public MutableLiveData<ArrayList<Boolean>> checkdata(String type, String id, prodect_data data)
    {
        check_founddata(type, id, data);
        MutableLiveData<ArrayList<Boolean>> mute=new MutableLiveData<ArrayList<Boolean>>();
        mute.setValue(arrayList);
        return mute;
    }
    public MutableLiveData<ArrayList<String>> getimage(ArrayList<Uri> arr)
    {
        images=new ArrayList<String>();
        for (int i=0;i<arr.size();i++)
        {
            upload_image(arr.get(i));
        }
        MutableLiveData<ArrayList<String>> mute=new MutableLiveData<ArrayList<String>>();
        mute.setValue(images);
        return mute;
    }
    public void  upload_image(Uri imageUri) {
        String image_id = UUID.randomUUID().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images_products/").child(image_id);
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
                    images.add(i.toString());
                } else {
                }
                listener.ongetdata();
            }
        });
    }

    public void delete_product(Fragment fragment,String type, String id) {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        database.child(type).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText( fragment.getActivity(),"تم حذف المنتج",Toast.LENGTH_LONG).show();
                   // listener.onadddata();
                    fragment.getActivity().onBackPressed();

                }
            }
        });
    }
}
