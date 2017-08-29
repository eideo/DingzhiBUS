package chenfei.com.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.MyAPP;
import chenfei.com.base.R;
import chenfei.com.utils.HorizontalDividerItemDecoration;

public class TravelListActivity extends BaseActivity {

    @BindView(R.id.travel_list)
    RecyclerView travelList;
    private TravelListAdapter madpter;
    private List<TravelDataInfo> travelDataInfos = new ArrayList<>();
     private String imageurl1="http://m.tuniucdn.com/fb2/t1/G2/M00/39/F3/Cii-TlhCcT-IXmNnACZefrCfpLoAAFF6ACbAgoAJl6W187_w500_h280_c1_t0.jpg";
    private String imageurl2="http://m.tuniucdn.com/fb2/t1/G2/M00/09/84/Cii-Tlib6J2ICr0UABcRghA28nkAAHFlADl7kwAFxGa132_w500_h280_c1_t0.jpg";
    private String imageurl3 ="http://m.tuniucdn.com/filebroker/cdn/snc/4a/a4/4aa41092e90d88a06f3391d606dd1c0e_w500_h280_c1_t0.jpg";
    private String imageurl4 ="http://m.tuniucdn.com/filebroker/cdn/vnd/da/c1/dac165c99f110d3b3c5ea2df62fa3203_w500_h280_c1_t0.jpg";
    private String url1="http://www.tuniu.com/tour/210041905";
    private String url2="http://www.tuniu.com/tour/210120954";
    private String url3="http://www.tuniu.com/tour/210520894";
    private String url4="http://www.tuniu.com/tour/210030491";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);
        ButterKnife.bind(this);
        InitView();
    }
    private void InitView(){
        InitBase();
        SetLeftVisible(true);
        SetMyTitle("旅游线路");
        travelDataInfos.add(new TravelDataInfo("武隆天生三硚-龙水峡地缝1日游",
                "享用价值58元/人高山生态自助餐，真正纯玩0购物，主城商圈定点免费接，提供自拍杆，无线耳麦讲解，充电宝",imageurl1,300,url1));
        travelDataInfos.add(new TravelDataInfo("长江三峡全景4日游",
                "重庆朝天门上船 提供接送 全程无缝对接 0购物 父母放心游",imageurl2,900,url2));
        travelDataInfos.add(new TravelDataInfo("万盛黑山谷-南川神龙峡漂流2日游",
                "纯玩0购物，含价值150元神龙峡漂流，清凉避暑新玩法，主城多区域定点接，报名即获途牛畅游卡",imageurl3,288,url3));
        travelDataInfos.add(new TravelDataInfo("酉阳桃花源-龚滩古镇-乌江画廊双汽2日游",
                "住宿升级 含乌江画廊游船，主城多地点接",imageurl4,360,url4));
        madpter = new TravelListAdapter(travelDataInfos);
        travelList.setLayoutManager(new LinearLayoutManager(this));
        travelList.setAdapter(madpter);
        travelList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .sizeResId(R.dimen.dp_0_5)
                .colorResId(R.color.c_e5e5e5)
                .build());
        madpter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent mintent = new Intent(TravelListActivity.this,WebviewActivity.class);
                mintent.putExtra("url",travelDataInfos.get(i).getUrl());
                startActivity(mintent);
            }
        });
    }

    public class TravelListAdapter extends BaseQuickAdapter<TravelDataInfo> {

        public TravelListAdapter(List<TravelDataInfo> data) {
            super(R.layout.item_travellist_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TravelDataInfo workInfo) {
            baseViewHolder.setText(R.id.text_name, workInfo.getTitle());
            baseViewHolder.setText(R.id.text_sub_title, workInfo.getSubtitle());
            baseViewHolder.setText(R.id.money, "￥"+workInfo.getMoney());
            ImageView imageView = baseViewHolder.getView(R.id.img_icon);
            imageView.setImageResource(R.mipmap.loaddingpic);
            String mUrls = workInfo.getImageurl();
            if (!TextUtils.isEmpty(mUrls)) {
                String[] urls = mUrls.split("#");
                ImageLoader.getInstance().displayImage(urls[0], imageView, MyAPP.defaultOptions);
            }

        }
    }

    private class TravelDataInfo{

        private String Title;
        private String subtitle;
        private String Imageurl;
        private int money;
        private String url;

        public TravelDataInfo(String title, String subtitle, String imageurl, int money, String url) {
            Title = title;
            this.subtitle = subtitle;
            Imageurl = imageurl;
            this.money = money;
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getImageurl() {
            return Imageurl;
        }

        public void setImageurl(String imageurl) {
            Imageurl = imageurl;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }
    }
}
