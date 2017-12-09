package personal.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import personal.myapplication.adapter.CategoryAdapter;
import personal.myapplication.dao.CategoryModel;
import personal.myapplication.interfaces.ApiRequestService;
import personal.myapplication.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity {
    ListView lstCategories;
    CategoryAdapter mAdapter;
    List<CategoryModel> categories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstCategories = (ListView) findViewById(R.id.lst_categories);
        mAdapter = new CategoryAdapter(categories, this);
        lstCategories.setAdapter(mAdapter);
        lstCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WelcomeActivity.this, SearchActivity.class);
                intent.putExtra(SearchActivity.KEY_CATEGORY_ID, categories.get(i).getItemName());
                startActivity(intent);
            }
        });
        fetchCategories();
    }

    void fetchCategories(){


        // category item
        ApiRequestService apiRequestService = ApiClient.getInstance().create(ApiRequestService.class);
        apiRequestService.getCategoryRequest().enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() > 0) {
                        List<CategoryModel> body = response.body();
                        categories.clear();
                        categories.addAll(body);
                        mAdapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(WelcomeActivity.this, "Some error has occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, "Some error has occurred", Toast.LENGTH_SHORT).show();
            }
        });



        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.welcome_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search){
            Intent intent = new Intent(WelcomeActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
