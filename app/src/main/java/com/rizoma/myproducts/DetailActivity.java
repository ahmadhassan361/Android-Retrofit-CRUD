package com.rizoma.myproducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rizoma.myproducts.utils.ApiInterface;
import com.rizoma.myproducts.utils.Product;
import com.rizoma.myproducts.utils.Respo;
import com.rizoma.myproducts.utils.Retrofit;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    Product product;
    ImageView img,btnBack,btnEdit,btnDelete;
    TextView tvName,tvPrice,tvStock,tvDesc;
    int id=0;
    ProgressBar progressBar;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img = findViewById(R.id.img);
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        tvName = findViewById(R.id.tvName);
        tvDesc = findViewById(R.id.tvDesc);
        tvPrice = findViewById(R.id.tvPrice);
        tvStock = findViewById(R.id.tvStock);
        progressBar = findViewById(R.id.progress_circular);
        scrollView = findViewById(R.id.tvData);
        Intent intent = getIntent();
         id = intent.getIntExtra("id",0);
        Log.i("TAG", String.valueOf(id));
        if (id != 0){
            getData(id);
        }
        btnDelete.setOnClickListener(view -> {
            scrollView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            if (id!= 0){
                ApiInterface anInterface = Retrofit.getRetrofit().create(ApiInterface.class);
                Call<Respo> responseData = anInterface.deleteProduct(id);
                responseData.enqueue(new Callback<Respo>() {
                    @Override
                    public void onResponse(@NonNull Call<Respo> call, @NonNull Response<Respo> response) {
                        if (response.isSuccessful()){
                            Respo respo = response.body();
                            assert product != null;
                            scrollView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            assert respo != null;
                            Toast.makeText(DetailActivity.this, ""+respo.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("TAG",respo.getMessage());
                            if (!respo.isError()){
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Respo> call, @NonNull Throwable t) {

                    }
                });

            }
        });

        btnBack.setOnClickListener(view -> onBackPressed());
        btnEdit.setOnClickListener(view -> {
            //need to implement
            Intent intent1 = new Intent(DetailActivity.this,AddProduct.class);
            intent1.putExtra("id",id);
            assert product != null;
            intent1.putExtra("name",product.getName());
            intent1.putExtra("desc",product.getDesc());
            intent1.putExtra("price",product.getPrice());
            intent1.putExtra("image",product.getImage());
            intent1.putExtra("stock",product.isInStock());
            startActivity(intent1);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(id);
    }

    private void getData(int id){
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = Retrofit.getRetrofit().create(ApiInterface.class);
        Call<Product> productData = apiInterface.getProduct(id);
        productData.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                if (response.isSuccessful()){
                    product = response.body();
                    assert product != null;
                    Log.i("TAG",product.getName());
                    setData(product);
                    scrollView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {

                Toast.makeText(DetailActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setData(Product product){
        try {
            Picasso.get().load(product.getImage()).into(img);
        }
        catch (Exception e){

        }
        tvName.setText(product.getName());
        tvDesc.setText(product.getDesc());
        tvPrice.setText(product.getPrice()+"$");
        if (product.isInStock()){
            tvStock.setText("In Stock");
            tvStock.setTextColor(Color.parseColor("#FF99CC00"));
        }else {
            tvStock.setText("Out Of Stock");
            tvStock.setTextColor(Color.parseColor("#FFE91E63"));
        }

    }
}