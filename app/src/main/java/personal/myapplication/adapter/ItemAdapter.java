package personal.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import personal.myapplication.R;
import personal.myapplication.dao.ItemModel;

/**
 * Created by root on 9/12/17.
 */

public class ItemAdapter extends BaseAdapter {

    List<ItemModel> items;
    Context mContex;
    LayoutInflater inflater;

    public ItemAdapter(List<ItemModel> items, Context mContex) {
        this.items = items;
        this.mContex = mContex;
        inflater = (LayoutInflater) mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.product_list_item, null);
            holder = new Holder();
            holder.txtItemName = (TextView) view.findViewById(R.id.txt_item_name);
            holder.txtItemDesc = (TextView)view.findViewById(R.id.txt_item_desc);
            holder.txtComment = (TextView)view.findViewById(R.id.txt_item_comment);
            holder.txtPrice = (TextView)view.findViewById(R.id.txt_item_price);
            holder.rateBar = (RatingBar) view.findViewById(R.id.rate_item);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }
        holder.txtItemName.setText(items.get(i).getName());
        holder.txtItemDesc.setText(items.get(i).getName());
        holder.txtPrice.setText(String.valueOf(items.get(i).getRate()) + " Rs");
        holder.txtComment.setText("Very good review");
        holder.rateBar.setMax(5);
        holder.rateBar.setRating(3.5f);
        return view;
    }

    class  Holder{
        TextView txtItemName, txtItemDesc, txtComment, txtPrice;
        RatingBar rateBar;

    }
}
