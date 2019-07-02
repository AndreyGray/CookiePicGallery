package andreyskakunenko.myapplication.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andreyskakunenko.myapplication.FullScreenPhoto;
import andreyskakunenko.myapplication.Interface.ILoadMore;
import andreyskakunenko.myapplication.Model.Item;
import andreyskakunenko.myapplication.R;

class LoadingViewHolder extends RecyclerView.ViewHolder
{
    public ProgressBar mProgressBar;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        mProgressBar = itemView.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder
{
    public TextView mOwnerTextView;
    public ImageView mImageView;
    public CardView cardItem;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.picture_item);
        mOwnerTextView = itemView.findViewById(R.id.owner_item);
        cardItem = itemView.findViewById(R.id.card_item);
    }
}

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Item> items;
    int visibleThreshold =5;
    int lastVisibleItem, totalItemCount;

    public MyAdapter(RecyclerView recyclerView,Activity activity, List<Item> items) {
        this.activity = activity;
        this.items = items;
        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = manager.getItemCount();
                lastVisibleItem = manager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <=(lastVisibleItem+visibleThreshold)){
                    if (loadMore != null)
                        loadMore.onLoadMore();
                    isLoading =true;

                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_gallery,viewGroup,false);
            return new ItemViewHolder(view);
        }else if (i == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading,viewGroup,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof ItemViewHolder){

            final Item item = items.get(i);
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, FullScreenPhoto.class);
                    intent.putExtra("file",item.getFile());
                    intent.putExtra("owner",item.getOwner());
                    intent.putExtra("license",item.getLicense());
                    activity.startActivity(intent);
                }
            });
            itemViewHolder.mOwnerTextView.setText(items.get(i).getOwner());
            Picasso.get()
                    .load(items.get(i).getFile())
                    .into(itemViewHolder.mImageView);
        }else {
            if(viewHolder instanceof LoadingViewHolder){
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder)viewHolder;
                loadingViewHolder.mProgressBar.setIndeterminate(true);
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setLoaded(){
        isLoading = false;
    }
}
