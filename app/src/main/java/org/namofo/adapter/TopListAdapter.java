package org.namofo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.namofo.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 * @Author: zhengjiong
 * Date: 2014-08-13
 * Time: 07:55
 */
public class TopListAdapter extends BaseAdapter{
    private Context mContext;

    public TopListAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class Holder{
        View topPaddingView;
        CircleImageView imgAvatar;
        TextView txtUsername;
        TextView txtInfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null){
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.top_list_item, null);

            holder.topPaddingView = convertView.findViewById(R.id.top_padding_view);
            holder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.top_list_user_avatar);
            holder.txtUsername = (TextView) convertView.findViewById(R.id.top_list_user_name);
            holder.txtInfo = (TextView) convertView.findViewById(R.id.top_list_info);

            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        if (position == 0) {
            holder.topPaddingView.setVisibility(View.VISIBLE);
        } else {
            holder.topPaddingView.setVisibility(View.GONE);
        }
        holder.imgAvatar.setImageResource(R.drawable.title_profile);
        holder.txtUsername.setText("妙光老师"+ (position+1));
        holder.txtInfo.setText("已连续签到"+ (600-position) +"天");

        return convertView;
    }
}
