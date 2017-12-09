package personal.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import personal.myapplication.R;
import personal.myapplication.dao.CategoryModel;

/**
 * Created by root on 9/12/17.
 */

public class CategoryAdapter extends BaseAdapter {
    List<CategoryModel> categories;
    Context mContext;
    LayoutInflater inflater;

    public CategoryAdapter(List<CategoryModel> categories, Context mContext) {
        this.categories = categories;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.category_list_item, null);
            holder = new Holder();
            holder.txtCategory = (TextView) view.findViewById(R.id.txt_category);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }
        holder.txtCategory.setText(categories.get(i).getItemName());
        return view;
    }

    class Holder{
        TextView txtCategory;

    }
}
