package com.rizoma.myproducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rizoma.myproducts.utils.Adapter;
import com.rizoma.myproducts.utils.ApiInterface;
import com.rizoma.myproducts.utils.Product;
import com.rizoma.myproducts.utils.Retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    List<Product> products;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        products = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_circular);
        floatingActionButton = findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(Dashboard.this,AddProduct.class)));

        fetchData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData()
    {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        ApiInterface apiInterface = Retrofit.getRetrofit().create(ApiInterface.class);
        Call<List<Product>> listData = apiInterface.getProducts();
        listData.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful()){
                    Log.i("TAG","response ok");
                    products.clear();
                    products  = response.body();
                    assert products != null;
                    Adapter adapter = new Adapter(products,Dashboard.this);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.i("TAG",t.getMessage());
                Toast.makeText(Dashboard.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}