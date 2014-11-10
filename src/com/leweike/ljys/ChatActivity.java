package com.leweike.ljys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity {

	private BaseAdapter adapter;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		listview = (ListView) findViewById(R.id.chat_listview);
		adapter = new ChatAdapter(this);
		listview.setAdapter(adapter);
	}

	class ChatAdapter extends BaseAdapter {

		private Context context;
		private View chat;
		
		public ChatAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			return 30;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint({ "InflateParams", "ViewHolder" })
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (position % 2 == 0) {
				chat = LayoutInflater.from(context).inflate(
						R.layout.chat_left_text, null, false);
				TextView button = (TextView) chat.findViewById(R.id.chat_left_text);
				button.setText("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
			}else {
				chat = LayoutInflater.from(context).inflate(
						R.layout.chat_left_image, null, false);
			}
			return chat;
		}

	}
}
