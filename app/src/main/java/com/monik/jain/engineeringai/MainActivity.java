package com.monik.jain.engineeringai;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.monik.jain.engineeringai.API.ServiceGenerator;
import com.monik.jain.engineeringai.adapters.PostAdapter;
import com.monik.jain.engineeringai.models.Hit;
import com.monik.jain.engineeringai.models.Posts;
import com.monik.jain.engineeringai.util.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;
import static com.monik.jain.engineeringai.customView.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, PostAdapter.onItemClick {

    private RecyclerView rvPosts;
    private PostAdapter postAdapter;
    private int startPage = 1;
    private SwipeRefreshLayout refreshLayout;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 50;
    private boolean isLoading = false;
    int itemCount = 0;
    private NoPaginate noPaginate;
    private TextView txtToolbar;
    private ArrayList<Hit> hits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
    }

    private void initControls() {
        findAllViews();
        postAdapter = new PostAdapter();
        postAdapter.setOnItemClick(this);
        rvPosts.setAdapter(postAdapter);
        noPaginate = NoPaginate.with(rvPosts)
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        //http or db request
                        callPostApi();
                    }
                }).setLoadingTriggerThreshold(0) //0 by default
                .build();
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        rvPosts.addItemDecoration(itemDecor);
        callPostApi();
    }

    private void callPostApi() {
        if (Utility.isNetworkAvailable(this)) {
            Call<Posts> postsCall = ServiceGenerator.createService().getPosts(startPage);
            postsCall.enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    Posts posts = response.body();
                    if (startPage == 1) {
                        hits.clear();
                        txtToolbar.setText(String.valueOf(0));
                    }
                    if (posts != null) {
                        hits.addAll(posts.getHits());
                        postAdapter.setItemList(hits);
                    }
                    startPage++;
                }

                @Override
                public void onFailure(Call<Posts> call, Throwable t) {
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    noPaginate.showError(true);
                }
            });
        } else {
            Utility.toast(this, getString(R.string.please_chk_conn));
        }
    }

    private void findAllViews() {
        rvPosts = findViewById(R.id.rvPosts);
        txtToolbar = findViewById(R.id.txtToolbar);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        startPage = 1;
        callPostApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noPaginate.unbind();
    }

    @Override
    public void onClick(List<Hit> hits) {
        int count = 0;
        for (int i = 0; i < hits.size(); i++) {
            if (hits.get(i).isEnabled()) {
                count++;
            }
        }
        txtToolbar.setText(String.valueOf(count));
    }
}
