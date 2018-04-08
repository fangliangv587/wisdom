package com.xz.cenco.assits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.cenco.common.widget.BaseTreeViewAdapter;
import com.xz.cenco.common.widget.TreeView;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.util.Util;

import java.util.List;


/**
 * TreeView demo adapter
 *
 * @author markmjw
 * @date 2014-01-04
 */
public class TreeViewAdapter extends BaseTreeViewAdapter {
    private LayoutInflater mInflater;

private Context context;

    private List<Track> data;


    public TreeViewAdapter(Context context, TreeView treeView) {
        super(treeView);
        this.context=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Record getChild(int groupPosition, int childPosition) {
        if (data == null) return null;
        Track track = data.get(groupPosition);
        Record record = track.getRecords().get(childPosition);

        return record;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (data == null) return 0;
        Track track = data.get(groupPosition);
        return track.getRecords().size();
    }

    @Override
    public Track getGroup(int groupPosition) {
        if (data == null) return null;
        Track track = data.get(groupPosition);
        return track;
    }

    @Override
    public int getGroupCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_view, null);
        }

        ChildHolder holder = getChildHolder(convertView);

        Record child = getChild(groupPosition, childPosition);
        String text = child.getInTime()+"-"+child.getOutTime()+"  "+ Util.getStayTimeString(child.getStayTime());

        Track track = getGroup(groupPosition);

        holder.name.setText(text);
        holder.state.setText("Test state...");
        holder.icon.setImageDrawable(track.getDrawable());
        return convertView;
    }

    private ChildHolder getChildHolder(final View view) {
        ChildHolder holder = (ChildHolder) view.getTag();
        if (null == holder) {
            holder = new ChildHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }

    private class ChildHolder {
        TextView name;
        TextView state;
        ImageView icon;

        public ChildHolder(View view) {
            name = (TextView) view.findViewById(R.id.contact_list_item_name);
            state = (TextView) view.findViewById(R.id.cpntact_list_item_state);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_group_view, null);
        }

        GroupHolder holder = getGroupHolder(convertView);

        Track track = data.get(groupPosition);
        String text = track.getAppName(context)+"("+track.getStanderTimeString()+")";
        holder.name.setText(text);
        holder.packageTv.setText(track.getPackageName());
        holder.onlineNum.setText(getChildrenCount(groupPosition) + "/" + getChildrenCount(groupPosition));
        if (isExpanded) {
            holder.indicator.setImageResource(R.drawable.indicator_expanded);
        } else {
            holder.indicator.setImageResource(R.drawable.indicator_unexpanded);
        }

        return convertView;
    }

    private GroupHolder getGroupHolder(final View view) {
        GroupHolder holder = (GroupHolder) view.getTag();
        if (null == holder) {
            holder = new GroupHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class GroupHolder {
        TextView name;
        ImageView indicator;
        TextView onlineNum;
        TextView packageTv;

        public GroupHolder(View view) {
            name = (TextView) view.findViewById(R.id.group_name);
            indicator = (ImageView) view.findViewById(R.id.group_indicator);
            onlineNum = (TextView) view.findViewById(R.id.online_count);
            packageTv = (TextView) view.findViewById(R.id.packageTv);
        }
    }

    @Override
    public void updateHeader(View header, int groupPosition, int childPosition, int alpha) {

        Track track = data.get(groupPosition);
        String text = track.getAppName(context)+"("+track.getStanderTimeString()+")";

        ((TextView) header.findViewById(R.id.group_name)).setText(text);
        ((TextView) header.findViewById(R.id.online_count)).setText(getChildrenCount
                (groupPosition) + "/" + getChildrenCount(groupPosition));
        header.setAlpha(alpha);
    }
}
