package personal.myapplication.interfaces;


import java.util.List;
import java.util.Map;

import personal.myapplication.dao.CategoryModel;
import personal.myapplication.dao.ItemModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ANZAR AHMAD on 12/9/2017.
 */

public interface ApiRequestService {
    /**
     * @return
     * @description used to get item list
     */
    @GET("common?service=getItem")
    Call<List<ItemModel>> getItemList(
            @QueryMap Map<String, String> options
    );
    /**
     * @return
     * @description used to get item list
     */
    @GET("common?service=getItem")
    Call<List<ItemModel>> getItemList();

    /**
     * @return
     * @description Used to get category list.
     */
    @GET("common?service=getCategory")
    Call<List<CategoryModel>> getCategoryRequest();
}