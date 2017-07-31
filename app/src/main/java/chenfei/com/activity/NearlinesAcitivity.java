package chenfei.com.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import chenfei.com.UI.PagerSlidingTabStrip;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.fragment.GerenFragment;
import chenfei.com.fragment.GuanfangLineFragment;
import chenfei.com.fragment.RunningLineFragment;

/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2015-8-4 上午11:49:09
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class NearlinesAcitivity extends BaseActivity{

	private ViewPager viewPager;
	private PagerSlidingTabStrip tabs;
	private DisplayMetrics dm;
	/**
	 * 开通路线的的Fragment
	 */
	private RunningLineFragment runningLineFragment;

	/**
	 * 官方招募的Fragment
	 */
	private GuanfangLineFragment guanfangLineFragment;

	/**
	 * 个人招募的Fragment
	 */
	private GerenFragment gerenFragment;


	private TextView title;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}

	protected void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_nearlines);
		dm = getResources().getDisplayMetrics();
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs = (PagerSlidingTabStrip) findViewById(R.id.nearlinestabs);
		tabs.setViewPager(viewPager);
		InitBase();
		SetMyTitle("附近路线");
		SetRightVisble(false);
		setTabsValue();
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 0, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "开通路线", "官方招募", "个人招募" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					if (runningLineFragment == null) {
						runningLineFragment = new RunningLineFragment();

					}
					return runningLineFragment;
				case 1:
					if (guanfangLineFragment == null) {
						guanfangLineFragment = new GuanfangLineFragment();
					}
					return guanfangLineFragment;
				case 2:
					if (gerenFragment == null) {
						gerenFragment = new GerenFragment();
					}
					return gerenFragment;
				default:
					return null;
			}
		}

	}
}
