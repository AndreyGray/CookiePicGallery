package andreyskakunenko.myapplication.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static andreyskakunenko.myapplication.Retrofit.Common.BASE_URL;


/** As part of our work with retrofit2 I must create retrofit client*/
public class RetrofitClient {
    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if(ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        return ourInstance;
    }

    private RetrofitClient() {
    }

}
