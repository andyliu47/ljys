package com.leweike.ljys;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.leweike.ljys.entity.MessageVo;
import com.leweike.ljys.msg.Status1Adapter;

/**
 * 项目: ljys 描述: 创建日期: 2014-11-3 下午5:06:17
 * 
 * @author
 */
public class HomeActivity extends BaseActivity {

	// data
	private List<MessageVo> designateList = new ArrayList<MessageVo>();
	private List<MessageVo> myList = new ArrayList<MessageVo>();
	private List<MessageVo> allList = new ArrayList<MessageVo>();

	private ListView listView;
	private BaseAdapter adapter;
	private ImageView homeLoading;
	private ImageView homeListLoading;
	private Animation operatingAnim;
	private int pageIndex = 1;
	private boolean isloading;

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		}
	};
	private OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
				if (view.getLastVisiblePosition() == view.getCount() - 1) {
					if (!isloading) {
						isloading = true;
						pageIndex++;
						new HomeAsyncTask().execute(String.valueOf(1));
					}
				}
				break;

			default:
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		}
	};

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		listView = (ListView) findViewById(R.id.home_listview);
		homeLoading = (ImageView) findViewById(R.id.home_loading);
		homeListLoading = (ImageView) findViewById(R.id.home_list_next_loading);
		operatingAnim = AnimationUtils.loadAnimation(this, R.animator.home_loading);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);

		new HomeAsyncTask().execute(String.valueOf(1));

		View pop = LayoutInflater.from(this).inflate(R.layout.home_more, null, false);

		final PopupWindow changeTeamWindow = new PopupWindow(pop, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		// 设置点击窗口外边窗口消失
		changeTeamWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
		ImageView changeTeam = (ImageView) findViewById(R.id.home_change_team);
		changeTeam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				changeTeamWindow.showAtLocation(v, Gravity.CENTER | Gravity.CENTER, 0, 0);
			}
		});
		TextView homeMoreCancel = (TextView) pop.findViewById(R.id.home_more_cancel);
		homeMoreCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				changeTeamWindow.dismiss();
			}
		});
	}

	private List<MessageVo> getData(int type) {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<MessageVo> list = new ArrayList<MessageVo>();
		MessageVo messageVo;
		for (int i = 0; i < 10; i++) {
			messageVo = new MessageVo();
			messageVo.setContent(((pageIndex - 1 )*10 + i) + "内容内容内容内容内容内容内容内容内容内容内容");
			messageVo.setCountMsg(1);
			messageVo.setNickName(((pageIndex - 1 )*10 + i) + "昵称");
			messageVo.setTime("10:12");
			list.add(messageVo);
		}
		return list;
	}

	class HomeAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (pageIndex == 1) {
				homeLoading.startAnimation(operatingAnim);
				adapter = new Status1Adapter(HomeActivity.this, designateList, myList, allList);
				listView.setAdapter(adapter);
				
			} else {
				homeListLoading.setVisibility(View.VISIBLE);
				homeListLoading.startAnimation(operatingAnim);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// Integer type = Integer.valueOf(params[0]);
			designateList.addAll(getData(1));
			myList.addAll(getData(2));
			allList.addAll(getData(3));
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (pageIndex == 1) {
				homeLoading.clearAnimation();
				homeLoading.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				listView.setOnItemClickListener(itemClickListener);
				listView.setOnScrollListener(scrollListener);
			} else {
				isloading = false;
				homeListLoading.clearAnimation();
				homeListLoading.setVisibility(View.GONE);
			}
			adapter.notifyDataSetChanged();
		}

	};

}
