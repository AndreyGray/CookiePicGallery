package andreyskakunenko.myapplication;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import andreyskakunenko.myapplication.Adapter.MyAdapter;
import andreyskakunenko.myapplication.Interface.ILoadMore;
import andreyskakunenko.myapplication.Interface.IMyAPI;
import andreyskakunenko.myapplication.Model.Item;
import andreyskakunenko.myapplication.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static andreyskakunenko.myapplication.Retrofit.Common.BASE_URL;
import static andreyskakunenko.myapplication.Retrofit.Common.isNetworkAvailable;

public class PicGalleryFragment extends Fragment {

    private final static String TAG = "PicGalleryFragment";
    List<Item> items = new ArrayList<>();
    MyAdapter mAdapter;
    IMyAPI mAPI;
    CompositeDisposable mCompositeDisposable;
    String searchWord = "sea";
    RecyclerView recyclerView;
    TextView noInternet;


    public static PicGalleryFragment newInstance(){
        return new PicGalleryFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_photo_gallery, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        if (isNetworkAvailable(getActivity())) {
            final SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "QueryTextSubmit: " + s);
                    updateItems(s);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
    }

    private void updateItems(String s) {
        searchWord = s;
        items.clear();
        loadData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNetworkAvailable(getActivity())) {
            mCompositeDisposable = new CompositeDisposable();
            Retrofit retrofit = RetrofitClient.getInstance();
            mAPI = retrofit.create(IMyAPI.class);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery,container,false);
        setHasOptionsMenu(true);
        noInternet=v.findViewById(R.id.no_internet);

        if (!isNetworkAvailable(getActivity())){
            noInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
        }else {
            loadData();
        }
        recyclerView = v.findViewById(R.id.pic_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return v;
    }

    private void loadData() {
        for (int i=0;i<30;i++){
            fetchingData();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter = new MyAdapter(recyclerView,getActivity(),items);
                recyclerView.setAdapter(mAdapter);
                //Set Load more event
                    mAdapter.setLoadMore(new ILoadMore() {
                        @Override
                        public void onLoadMore() {
                            if (items.size() <=50){
                                //items.add(null);
                                //mAdapter.notifyItemInserted(items.size()-1);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //items.remove(items.size()-1);
                                        //mAdapter.notifyItemRemoved(items.size());

                                        //More data
                                        int index = items.size();
                                        int end = index+30;
                                        for(int i = index;i<end;i++){
                                            fetchingData();
                                        }
                                        mAdapter.notifyDataSetChanged();
                                        mAdapter.setLoaded();

                                    }
                                },1000);
                            }else {
                                Toast.makeText(getActivity(), " Load data completed! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }
        },2000);



    }


    private void fetchingData(){
        mCompositeDisposable.add(mAPI.getResults(BASE_URL+searchWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Item>() {

                    @Override
                    public void accept(Item item) throws Exception {
                        items.add(item);
                    }
                })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable!=null)mCompositeDisposable.clear();

    }
}
