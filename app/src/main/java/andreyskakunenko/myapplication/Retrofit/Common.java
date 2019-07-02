package andreyskakunenko.myapplication.Retrofit;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

public class Common {
    private Context mContext;
    public static final String BASE_URL = "https://loremflickr.com/json/320/240/";

    private Common(@NonNull Context context){
        mContext = context.getApplicationContext();
    }


    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
    }


}
