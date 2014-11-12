package com.leweike.ljys;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.leweike.ljys.entity.MessageVo;
import com.leweike.ljys.msg.StatusChangeAdapter;

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
	private ImageView homeLoading; //changestatus loading
	private AnimationSet animationSet; // loading rotate
	private int pageIndex = 1; //message page
	private int currentType = 1; // status type
	private boolean isloading; // loading data?
	private AsyncTask<String, Integer, String> asyncTask;

	// footer
	private ImageButton messageButton;
	private ImageButton patientButton;
	private ImageButton settingButton;

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		}
	};
	private OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				if (view.getLastVisiblePosition() == view.getCount() - 1) {
					if (!isloading) {
						isloading = true;
						pageIndex++;
						asyncTask = new HomeAsyncTask();
						asyncTask.execute(String.valueOf(1));
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
		animationSet = rotateAnimation();
		homeLoading = (ImageView) findViewById(R.id.home_loading);

		initChangeStatus();
		initFooter();
		initPopWindow();

	}

	TextView status1, status2, status3;

	private void initChangeStatus() {
		status1 = (TextView) findViewById(R.id.home_message_status_1);
		status2 = (TextView) findViewById(R.id.home_message_status_2);
		status3 = (TextView) findViewById(R.id.home_message_status_3);

		status1.setOnClickListener(changeStatusListener);
		status2.setOnClickListener(changeStatusListener);
		status3.setOnClickListener(changeStatusListener);

		changeStatus(1);
	}

	private void changeStatus(int type) {
		currentType = type;
		pageIndex = 1;
		if (asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
			asyncTask.cancel(true);
		}
		designateList.clear();
		myList.clear();
		allList.clear();
		
		asyncTask = new HomeAsyncTask();
		asyncTask.execute(String.valueOf(1));
		if (type == 1) {
			status1.setTextColor(getResources().getColor(R.color.white));
			status1.setBackgroundResource(R.drawable.home_tab_select);
			status2.setTextColor(getResources().getColor(R.color.blue));
			status2.setBackgroundColor(getResources().getColor(R.color.gray));
			status3.setTextColor(getResources().getColor(R.color.blue));
			status3.setBackgroundColor(getResources().getColor(R.color.gray));
		} else if (type == 2) {
			status2.setTextColor(getResources().getColor(R.color.white));
			status2.setBackgroundResource(R.drawable.home_tab_select);
			status1.setTextColor(getResources().getColor(R.color.blue));
			status1.setBackgroundColor(getResources().getColor(R.color.gray));
			status3.setTextColor(getResources().getColor(R.color.blue));
			status3.setBackgroundColor(getResources().getColor(R.color.gray));
		} else if (type == 3) {
			status3.setTextColor(getResources().getColor(R.color.white));
			status3.setBackgroundResource(R.drawable.home_tab_select);
			status1.setTextColor(getResources().getColor(R.color.blue));
			status1.setBackgroundColor(getResources().getColor(R.color.gray));
			status2.setTextColor(getResources().getColor(R.color.blue));
			status2.setBackgroundColor(getResources().getColor(R.color.gray));
		}
	}

	private OnClickListener changeStatusListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_message_status_1:
				if (currentType != 1) {
					changeStatus(1);
				}
				break;
			case R.id.home_message_status_2:
				if (currentType != 2) {
					changeStatus(2);
				}
				break;
			case R.id.home_message_status_3:
				if (currentType != 3) {
					changeStatus(3);
				}
				break;
			default:
				break;
			}
		}
	};

	@SuppressLint("InflateParams")
	private void initPopWindow() {
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
		List<MessageVo> list = new ArrayList<MessageVo>();
		MessageVo messageVo;
		for (int i = 0; i < 10; i++) {
			messageVo = new MessageVo();
			messageVo.setContent(((pageIndex - 1) * 10 + i) + "内容内容内容内容内容内容内容内容内容内容内容");
			messageVo.setCountMsg(1);
			messageVo.setNickName(((pageIndex - 1) * 10 + i) + "昵称");
			messageVo.setTime("10:12");
			list.add(messageVo);
		}
		return list;
	}

	private AnimationSet rotateAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(500);
		rotateAnimation.setRepeatCount(-1);
		LinearInterpolator li = new LinearInterpolator();
		rotateAnimation.setInterpolator(li);
		animationSet.addAnimation(rotateAnimation);
		return animationSet;
	}

	class HomeAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (pageIndex == 1) {
				homeLoading.startAnimation(animationSet);
				adapter = new StatusChangeAdapter(HomeActivity.this, currentType, currentType == 1 ? designateList : null, myList, allList);
				listView.setAdapter(adapter);
			} else {
				ImageView imageView = (ImageView) listView.findViewById(R.id.home_listview_loading);
				imageView.startAnimation(animationSet);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			/*
			 * designateList.addAll(getData(1)); myList.addAll(getData(2));
			 */
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
			}
			adapter.notifyDataSetChanged();
		}

	};

	private void initFooter() {
		messageButton = (ImageButton) findViewById(R.id.home_footer_message_btn);
		patientButton = (ImageButton) findViewById(R.id.home_footer_patient_btn);
		settingButton = (ImageButton) findViewById(R.id.home_footer_setting_btn);

		messageButton.setOnClickListener(footerListener);
		patientButton.setOnClickListener(footerListener);
		settingButton.setOnClickListener(footerListener);
	}

	private OnClickListener footerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.home_footer_message_btn:
				callAblum();
				break;
			case R.id.home_footer_patient_btn:
				callCamera(String.valueOf(System.currentTimeMillis()));
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackAblumOrCamera(requestCode, data);
	}

}
