package expandablelistviewdemo.expandablelistviewdemo;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListAdapter extends BaseExpandableListAdapter{

	private Context _context;
	private List<String> header; // header titles
	// Child data in format of header title, child title
	private HashMap<String, List<String>> child;
	private int[] mDrawableList=new int[]{R.mipmap.a60,R.mipmap.b60,R.mipmap.c60,R.mipmap.d60,R.mipmap.e60,R.mipmap.f60,R.mipmap.g60,R.mipmap.j60,R.mipmap.k60};
	private int[] mDrawableList2=new int[]{R.mipmap.second_1,R.mipmap.second_2,R.mipmap.second_3,R.mipmap.second_4,R.mipmap.second_5,R.mipmap.second_6,R.mipmap.second_7};

	public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
		this._context = context;
		this.header = listDataHeader;
		this.child = listChildData;
	}
	
	@Override
	public int getGroupCount() {
		// Get header size
		return this.header.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// return children count
		return this.child.get(this.header.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// Get header position
		return this.header.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// This will return the child
		return this.child.get(this.header.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		// Getting header title
		final String headerTitle = (String) getGroup(groupPosition);
		
		// Inflating header layout and setting text
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.header, parent, false);
		}

		TextView header_text = (TextView) convertView.findViewById(R.id.header);
		header_text.setText(headerTitle);
		
		// If group is expanded then change the text into bold and change the
		// icon
		Drawable drawableFirst = _context.getResources().getDrawable(mDrawableList[groupPosition]);
		drawableFirst.setBounds(0,0,50,50);
        if (isExpanded) {
			header_text.setTypeface(null, Typeface.BOLD);
			Drawable drawableUp = _context.getResources().getDrawable(R.drawable.ic_up);
			drawableUp.setBounds(0,0,50,50);
//			header_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.a60, 0, R.drawable.ic_up, 0);
            header_text.setCompoundDrawables(drawableFirst,null,drawableUp,null);
		} else {
			// If group is not expanded then change the text back into normal
			// and change the icon
			Drawable drawableDown = _context.getResources().getDrawable(R.drawable.ic_down);
			drawableDown.setBounds(0,0,50,50);
			header_text.setTypeface(null, Typeface.NORMAL);
            header_text.setCompoundDrawables(drawableFirst,null,drawableDown,null);
		}
		header_text.setTextColor(Color.BLACK);
		/*
		长按响应处理，R.id.child和R.id.ll_child只要是自己项目里的资源就可以，必须与 getChildView方法中一直
		 */
		convertView.setTag(R.id.child, groupPosition);//记录groupPosition
		convertView.setTag(R.id.ll_child, -1);//-1是随便定义的，区分childview和groupview

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// Getting child text
		final String childText = (String) getChild(groupPosition, childPosition);
		// Inflating child layout and setting textview
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.childs, parent, false);
		}

		TextView child_text = (TextView) convertView.findViewById(R.id.child);
        Drawable drawable = _context.getResources().getDrawable(mDrawableList2[childPosition]);
        drawable.setBounds(0,0,40,40);
        child_text.setCompoundDrawables(drawable,null,null,null);
        child_text.setTextColor(Color.BLACK);
		child_text.setText(childText);

		/*
		长按响应处理
		 */
		convertView.setTag(R.id.child, groupPosition);//记录groupPosition
		convertView.setTag(R.id.ll_child, childPosition);//记录childPosition
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
