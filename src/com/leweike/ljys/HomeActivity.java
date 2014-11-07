package com.leweike.ljys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 项目: ljys
 * 描述: 
 * 创建日期: 2014-11-3 下午5:06:17 
 * @author
 */
public class HomeActivity extends BaseActivity {

	private ListView listView;
	private BaseAdapter adapter;
	private int count = 30;
	private ImageView changeTeam;
	private PopupWindow changeTeamWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		listView = (ListView) findViewById(R.id.home_listview);
		
		adapter = new HomeAdapter(this);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
				startActivity(intent);
			}
		});
		
		View pop = LayoutInflater.from(this).inflate(R.layout.home_more, null,false);
		
		changeTeamWindow = new PopupWindow(pop, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, true);
		changeTeamWindow.setBackgroundDrawable(new BitmapDrawable()); 
        //设置点击窗口外边窗口消失 
		changeTeamWindow.setOutsideTouchable(true); 
        // 设置此参数获得焦点，否则无法点击 
        pop.setFocusable(true); 
		changeTeam = (ImageView) findViewById(R.id.home_change_team);
		changeTeam.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeTeamWindow.showAtLocation(v, Gravity.CENTER|Gravity.CENTER, 0, 0);
			}
		});
	}

	
	private class HomeAdapter extends BaseAdapter {

		private Context context;
		private View list;
		
		public HomeAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (position <= count) {
				list = LayoutInflater.from(context).inflate(R.layout.home_list, null, false);
				//ImageView photo = (ImageView) list.findViewById(R.id.home_list_photo);
				TextView time = (TextView) list.findViewById(R.id.home_list_time);
				time.setText("昨天 10:12");
				TextView content = (TextView) list.findViewById(R.id.home_list_content);
				content.setText("图文消息图文消息图文消息图文消息图文消息图文消息图文消息");
				TextView nickname = (TextView) list.findViewById(R.id.home_list_nickname);
				nickname.setText("与时间为伴");
				return list;
			}else {
			}
			return null;
		}
		
	}
}
