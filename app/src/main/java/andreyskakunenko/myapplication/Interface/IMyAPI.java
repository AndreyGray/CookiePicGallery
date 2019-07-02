package andreyskakunenko.myapplication.Interface;

import andreyskakunenko.myapplication.Model.Item;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IMyAPI {

    @GET
    Observable<Item> getResults(@Url String url);

}
