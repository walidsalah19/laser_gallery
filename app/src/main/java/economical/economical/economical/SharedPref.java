package economical.economical.economical;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {
    SharedPreferences preferences;
    Context context;
    public SharedPref(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void settoken(String language){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",language);
        editor.apply();
    }

    public String gettoken(){
        return preferences.getString("token","");
    }
}
