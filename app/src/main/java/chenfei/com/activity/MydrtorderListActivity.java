package chenfei.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import chenfei.com.adapter.GuanfangLinelistadapter;
import chenfei.com.adapter.Myorderlistadapter;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.category.DrtorderItem;
import chenfei.com.category.GetLinesInfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.HorizontalDividerItemDecoration;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

;

/**
 * * @author 作者 E-mail:
 *
 * @version 1.0
 * @date 创建时间：2015-7-24 上午10:42:51
 * @parameter
 * @return
 */
public class MydrtorderListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.line_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Context context;
    private  int TOTAL_COUNTER = 1000;
    private static final int PAGE_SIZE = 10;
    private int delayMillis = 1000;
    private int mCurrentCounter = 0;
    public Vector<DrtorderItem> myorderItems = new Vector<DrtorderItem>();
    private Myorderlistadapter madpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.myorderlist);
        ButterKnife.bind(this);
        InitBase();
        SetLeftVisible(true);
        SetMyTitle("我的订单");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .sizeResId(R.dimen.dp_0_5)
                .colorResId(R.color.c_e5e5e5)
                .build());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        LoadLinelist();
    }


    private void showDetail(DrtorderItem item) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MydrtorderListActivity.this,
                MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", item);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        LoadLinelist();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    madpter.notifyDataChangedAfterLoadMore(false);
                    View view = getLayoutInflater().inflate(R.layout.no_moredata, (ViewGroup) mRecyclerView.getParent(), false);
                    madpter.addFooterView(view);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            madpter.notifyDataChangedAfterLoadMore(getData(myorderItems, PAGE_SIZE), true);
                            mCurrentCounter = madpter.getData().size();
                        }
                    }, delayMillis);
                }
            }


        });
    }

    private void initAdapter() {
        madpter = new Myorderlistadapter(this, R.layout.myorderlistitem, myorderItems, PAGE_SIZE);
        madpter.openLoadAnimation();
        mRecyclerView.setAdapter(madpter);
        mCurrentCounter = madpter.getData().size();
        madpter.setOnLoadMoreListener(this);
        madpter.openLoadMore(PAGE_SIZE, true);//or call mQuickAdapter.setPageSize(PAGE_SIZE);  mQuickAdapter.openLoadMore(true);
        madpter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    public static List<DrtorderItem> getData(List<DrtorderItem> data, int dataSize) {
        List<DrtorderItem> newlist = new ArrayList<DrtorderItem>();
        if (data.size() < dataSize) {
            newlist = data;
        } else {
            for (int i = 0; i < dataSize; i++) {
                newlist.add(data.get(i));
            }
        }
        return newlist;
    }
    public void LoadLinelist() {
        OkHttpUtils
                .post(ApiInterface.Getmydrtorderlist)
                .params("userid", getStringData("userid", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(MydrtorderListActivity.this, "返回数据为空");

                            return;
                        }
                        // analyze JSON string
                        try {
                            myorderItems.removeAllElements();
                            JSONObject jsonObject = new JSONObject(jsonstring);
                            JSONArray jsonArray = jsonObject.getJSONArray("myorderlist");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonItem = jsonArray.getJSONObject(i);
                                DrtorderItem item = new DrtorderItem();
                                item.lineid = jsonItem.getString("lineid");
                                item.orderid = jsonItem.getString("orderid");
                                item.userid = jsonItem.getString("userid");
                                item.startstop = jsonItem.getString("startstop");
                                item.endstop = jsonItem.getString("endstop");
                                item.upstop = jsonItem.getString("upstop");
                                item.downstop = jsonItem.getString("downstop");
                                item.distance = jsonItem.getString("distance");
                                item.runtime = jsonItem.getString("runtime");
                                item.buytime = jsonItem.getString("buytime");
                                item.ticketnum = jsonItem.getString("ticketnum");
                                item.paymoney = jsonItem.getString("paymoney");
                                item.totolmoney = jsonItem.getString("totolmoney");
                                item.buytype = jsonItem.getString("buytype");
                                item.city = jsonItem.getString("city");
                                myorderItems.add(item);
                            }
                            initAdapter();
                            mRecyclerView.setAdapter(madpter);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return;
                        }
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        // TODO Auto-generated method stub
                        ToastUtils.showLong(MydrtorderListActivity.this, "网络连接失败");
                    }
                });
    }
}
