package com.leweike.ljys;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.leweike.ljys.entity.UserInfo;

/**
 * 项目: ljys
 * 描述: 
 * 创建日期: 2014-11-3 下午5:06:17 
 * @author
 */
public class HomeActivity extends BaseActivity {

	private ListView listView;
	private BaseAdapter adapter;
	private List<UserInfo> userInfos;
	private int count = 30;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		listView = (ListView) findViewById(R.id.home_list_view);
		
		adapter = new HomeAdapter(this);
		
		listView.setAdapter(adapter);
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
