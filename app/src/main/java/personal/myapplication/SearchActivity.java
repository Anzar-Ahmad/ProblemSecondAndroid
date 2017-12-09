package personal.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import personal.myapplication.adapter.ItemAdapter;
import personal.myapplication.dao.ItemModel;
import personal.myapplication.interfaces.ApiRequestService;
import personal.myapplication.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {
    public static final String KEY_CATEGORY_ID = "key_category_id";
    String categoryId = null;
    ListView listItem;
    List<ItemModel> items = new ArrayList<>();
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        categoryId = getIntent().getStringExtra(KEY_CATEGORY_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        listItem = (ListView) findViewById(R.id.lst_item);
        itemAdapter = new ItemAdapter(items, this);
        listItem.setAdapter(itemAdapter);
        showItem();
    }



    void showItem(){

        /*for (int i = 0 ;  i < 20; i++){
            Item item = new Item();
            item.id = String.valueOf(i);
            item.name = String.valueOf(i) + "name";
            item.description = String.valueOf(i) + "desc";
            item.comment = String.valueOf(i) + "Testing comment";
            item.rate = String.valueOf(2.5f);
            items.add(item);
        }*/

        if(categoryId != null){
            Map<String, String> data = new HashMap<>();
            data.put("category", categoryId);
            ApiRequestService apiRequestService = ApiClient.getInstance().create(ApiRequestService.class);

            apiRequestService.getItemList(data).enqueue(new Callback<List<ItemModel>>() {
                @Override
                public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().size() > 0) {
                            List<ItemModel> body = response.body();
                            items.clear();
                            items.addAll(body);
                            itemAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(SearchActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                    Log.e("Str", "Error " + t.getLocalizedMessage());
                    Toast.makeText(SearchActivity.this, "Some error has occured", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            ApiRequestService apiRequestService = ApiClient.getInstance().create(ApiRequestService.class);

            apiRequestService.getItemList().enqueue(new Callback<List<ItemModel>>() {
                @Override
                public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().size() > 0) {
                            List<ItemModel> body = response.body();
                            items.clear();
                            items.addAll(body);
                            itemAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(SearchActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                    Log.e("Str", "Error " + t.getLocalizedMessage());
                    Toast.makeText(SearchActivity.this, "Some error has occured", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.search_item_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        // Detect SearchView icon clicks
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemsVisibility(menu, searchItem, false);
            }
        });
        // Detect SearchView close
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItemsVisibility(menu, searchItem, true);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }else if(item.getItemId() == R.id.action_filter){
            Intent intent = new Intent(SearchActivity.this, FilterActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i=0; i<menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }
}
