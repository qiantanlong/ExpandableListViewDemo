package expandablelistviewdemo.expandablelistviewdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int DOWNLOAD_RETRY = 1;
    private static final int DOWNLOAD_DEL = 2;
    private static final int DOWNLOAD_START = 3;
    private static ExpandableListView expandableListView;
    private static ExpandableListAdapter adapter;
    private HashMap<String, List<String>> hashMap;
    private ArrayList<String> header;
    private int mId = -1;
    private int mGroupPosition;
    private int mChildPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
        setItems();//设置数据
        longClickItem();
        // 设置group条目的图片
        expandableListView.setGroupIndicator(null);
        //第二级条目中的子条目的点击监听，必须在ExpandableListAdapter中的isChildSelectable（）中返回true，否则child点击无效
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // 设置选中背景
                //setItemChecked(groupPosition, childPosition);
                List<String> strings = hashMap.get(header.get(groupPosition));
                Log.i("child", strings.get(childPosition));
                Toast.makeText(MainActivity.this, strings.get(childPosition), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        //group条目点击事件，设置返回true后会child条目不会展开和收缩，配合全部展开，可以用来阻止条目收缩
        /*expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				Log.i("onGroupClick","onGroupClick");
				return true;
			}
		});*/
        //当条目折叠后的监听
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.i("onGroupCollapse", "onGroupCollapse");//折叠监听


            }
        });
        /*//条目展开后的监听
		expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				Log.i("onGroupExpand","onGroupExpand");//展开监听
				Toast.makeText(MainActivity.this,header.get(groupPosition),Toast.LENGTH_SHORT).show();
				}
		});*/


    }

    private void makeTextShort(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    private void setItemChecked(int groupPosition, int childPosition) {
        if (expandableListView == null) {
            return;
        }

        this.mGroupPosition = groupPosition;
        this.mChildPosition = childPosition;

        int numberOfGroupThatIsOpened = 0;
        for (int i = 0; i < groupPosition; i++) {
            if (expandableListView.isGroupExpanded(i)) {
                numberOfGroupThatIsOpened += adapter.getChildrenCount(i);
            }
        }
        int position = numberOfGroupThatIsOpened + groupPosition
                + childPosition + 1;
        if (!expandableListView.isItemChecked(position)) {
            expandableListView.setItemChecked(position, true);
        }
    }


    // 设置 headers 和 childs ExpandableListView
    void setItems() {
        //header的list数据集合
        header = new ArrayList<String>();

        //child items数据集合
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();
        List<String> child4 = new ArrayList<String>();
        List<String> child5 = new ArrayList<String>();


        // Hashmap 存储 header 和 child对应集合
        hashMap = new HashMap<String, List<String>>();

        // 添加headers 数据到集合
        for (int i = 0; i < 5; i++) {
            header.add("Group" + i);
        }
        // 添加child数据到集合
        for (int i = 0; i < 5; i++) {
            child1.add(header.get(0) + ":" + "Item" + i);
        }
        for (int i = 0; i < 7; i++) {
            child2.add(header.get(1) + ":" + "Item" + i);
        }
        for (int i = 0; i < 3; i++) {
            child3.add(header.get(2) + ":" + "Item" + i);
        }
        for (int i = 0; i < 8; i++) {
            child4.add(header.get(3) + ":" + "Item" + i);
        }
        for (int i = 0; i < 6; i++) {
            child5.add(header.get(4) + ":" + "Item" + i);
        }


        // header和childs集合数据到hashmap
        hashMap.put(header.get(0), child1);
        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        hashMap.put(header.get(3), child4);
        hashMap.put(header.get(4), child5);


        adapter = new ExpandableListAdapter(MainActivity.this, header, hashMap);
        // 给expandablelistview设置adapter
        expandableListView.setAdapter(adapter);

		/*1、首次加载全部展开：

		for (int i = 0; i < header.size(); i++) {
			expandableListView.expandGroup(i);//默认展开某个group也用这个方法
		}
		*/

        // 2.这里是控制只有一个group展开的效果
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.i("onGroupExpand", "onGroupExpand");//展开监听
                Toast.makeText(MainActivity.this, header.get(groupPosition), Toast.LENGTH_SHORT).show();
                int childCount = adapter.getChildrenCount(groupPosition);
                Log.i("childCount", childCount + "");
                //展开后取消所有条目选择状态，防止出现不必要的条目被选择
                for (int i = 0; i < childCount; i++) {
                    //i就是childposition
                    expandableListView.setItemChecked(groupPosition + i, false);
                }
                for (int i = 0; i < adapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });

    }

    /*
    长按条目的处理方法
     */
    private void longClickItem() {
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickListIteam(view);
                return true;
            }
        });
    }
    //长按处理方法
    private void longClickListIteam(View view) {
        final int groupPos = (Integer) view.getTag(R.id.child);
        final int childPos = (Integer) view.getTag(R.id.ll_child);
        Log.i("yu", "groupPos:" + groupPos + ",childPos:" + childPos);
        if (childPos == -1) {         //如果得到child位置的值为-1，则是操作group
            Log.i("yu", "操作group组件");
        } else {
            Log.i("yu", "操作child组件");     //否则，操作child
        }
    }
}
