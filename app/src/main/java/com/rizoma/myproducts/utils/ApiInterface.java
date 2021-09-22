package com.rizoma.myproducts.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api/products")
    Call<List<Product>>getProducts();
    @GET("api/products")
    Call<Product>getProduct(@Query("id") int id);
    @POST("api/products")
    Call<Respo> addProduct(@Body Product product);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "api/products", hasBody = true)
    Call<Respo> deleteProduct(@Field("id") int id);

    @PUT("api/products")
    Call<Respo> updateProduct(@Body Product product);



}
