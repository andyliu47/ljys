package com.leweike.ljys.msg;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leweike.ljys.R;
import com.leweike.ljys.entity.MessageVo;

public class StatusChangeAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<MessageVo> designateList;
	private List<MessageVo> myList;
	private List<MessageVo> allList;
	private View view;
	private int type;
	private AnimationSet animationSet;

	private int count, designateHeader, designateStart, designateEnd, myHeader, myStart, myEnd, allHeader, allStart, allEnd;

	public StatusChangeAdapter(Context context, int type, List<MessageVo> designateList, List<MessageVo> myList, List<MessageVo> allList) {
		super();
		this.type = type;
		this.layoutInflater = LayoutInflater.from(context);
		this.designateList = designateList;
		this.myList = myList;
		this.allList = allList;
		animationSet = rotateAnimation();
		calculateLoaction();
	}

	private AnimationSet rotateAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		RotateAnimation rotateAnimation = new RotateAnimation(0, -359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(500);
		rotateAnimation.setRepeatCount(-1);
		LinearInterpolator li = new LinearInterpolator();
		rotateAnimation.setInterpolator(li);
		animationSet.addAnimation(rotateAnimation);
		return animationSet;
	}
	
	public void calculateLoaction() {
		if (designateList == null || designateList.size() == 0) {
			designateHeader = designateStart = designateEnd = myHeader = 0;
		} else {
			designateHeader = 0;
			designateStart = 1;
			designateEnd = designateList.size();
			myHeader = designateEnd + 1;
		}
		if (myList == null || myList.size() == 0) {
			myStart = myEnd = allHeader = myHeader;
		} else {
			myStart = myHeader + 1;
			myEnd = myHeader + myList.size();
			allHeader = myEnd + 1;
		}
		if (allList == null || allList.size() == 0) {
			allStart = allEnd = count = allHeader;
		} else {
			allStart = allHeader + 1;
			allEnd = allHeader + allList.size();
			count = allEnd + 2;
		}

	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		int type = getType(position);
		if (type == 1 || type == 3 || type == 5) {
			return null;
		} else if (type == 2) {
			return designateList.get(position - designateStart);
		} else if (type == 4) {
			return myList.get(position - myStart);
		} else if (type == 6) {
			return allList.get(position - allStart);
		} else if (type == 7) {
			return null;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		int type = getType(position);
		if (type == 1 || type == 3 || type == 5) {
			return 0;
		} else if (type == 2) {
			return position - designateStart;
		} else if (type == 4) {
			return position - myStart;
		} else if (type == 6) {
			return position - allStart;
		}
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getType(position);
		MessageVo messageVo = null;
		if (type == 1) {
			view = layoutInflater.inflate(R.layout.home_header, null);
			TextView textView = (TextView) view.findViewById(R.id.home_header_name);
			textView.setText("指派");
			return view;
		} else if (type == 2) {
			messageVo = designateList.get(position - designateStart);
		} else if (type == 3) {
			view = layoutInflater.inflate(R.layout.home_header, null);
			TextView textView = (TextView) view.findViewById(R.id.home_header_name);
			textView.setText("我的消息");
			return view;
		} else if (type == 4) {
			messageVo = myList.get(position - myStart);
		} else if (type == 5) {
			view = layoutInflater.inflate(R.layout.home_header, null);
			TextView textView = (TextView) view.findViewById(R.id.home_header_name);
			textView.setText("更多消息");
			return view;
		} else if (type == 6) {
			messageVo = allList.get(position - allStart);
		} else if (type == 7){
			view = layoutInflater.inflate(R.layout.home_listview_loading, null);
			ImageView footer = (ImageView) view.findViewById(R.id.home_listview_loading);
			footer.startAnimation(animationSet);
			return view; 
		}
		view = layoutInflater.inflate(R.layout.home_list, null);
		TextView time = (TextView) view.findViewById(R.id.home_list_time);
		time.setText(messageVo.getTime());
		TextView content = (TextView) view.findViewById(R.id.home_list_content);
		content.setText(messageVo.getContent());
		TextView nickname = (TextView) view.findViewById(R.id.home_list_nickname);
		nickname.setText(messageVo.getNickName());
		TextView msgCount = (TextView) view.findViewById(R.id.home_list_countmsg);
		if (this.type == 1) {
			msgCount.setText(String.valueOf(messageVo.getCountMsg()));
		}else {
			msgCount.setVisibility(View.GONE);
		}
		
		return view;
	}

	/**
	 * 1 指派标题 2 指派内容 3 我的消息标题 4 我的消息内容 5 所有消息标题 6 所有消息标题
	 * 
	 * @param position
	 * @return
	 */
	private int getType(int position) {
		if (position == count - 1) {
			return 7;
		}else if (position >= allStart && position <= allEnd) {
			return 6;
		} else if (position == allHeader) {
			return 5;
		} else if (position >= myStart && position <= myEnd) {
			return 4;
		} else if (position == myHeader) {
			return 3;
		} else if (position >= designateStart && position <= designateEnd) {
			return 2;
		} else if (position == designateHeader) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public void notifyDataSetChanged() {
		calculateLoaction();
		super.notifyDataSetChanged();
	}

}
