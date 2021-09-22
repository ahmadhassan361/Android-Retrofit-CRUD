package com.rizoma.myproducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rizoma.myproducts.utils.ApiInterface;
import com.rizoma.myproducts.utils.Product;
import com.rizoma.myproducts.utils.Respo;
import com.rizoma.myproducts.utils.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProduct extends AppCompatActivity {

    ApiInterface apiInterface;
    EditText etName,etDesc,etImage,etPrice;
    CheckBox etStock;
    ImageView btnBack;
    Button btnAdd;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        apiInterface = Retrofit.getRetrofit().create(ApiInterface.class);
        tvTitle = findViewById(R.id.tvHeading);
        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etImage = findViewById(R.id.etImage);
        etPrice = findViewById(R.id.etPrice);
        etStock = findViewById(R.id.etStock);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> onBackPressed());
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        int price = intent.getIntExtra("price",0);
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        String image = intent.getStringExtra("image");
        boolean stock = intent.getBooleanExtra("stock",false);
        if (id !=0){
            String update = "Update Product";
            tvTitle.setText(update);
            setValues(id,name,desc,image,price,stock);
            btnAdd.setText(update);
        }

        btnAdd.setOnClickListener(view -> getData(id));
    }

    private void setValues(int id, String name, String desc, String image, int price, boolean stock) {
        etName.setText(name);
        etDesc.setText(desc);
        etImage.setText(image);
        etPrice.setText(String.valueOf(price));
        if (stock){
            etStock.setChecked(true);
        }
    }

    private void getData(int id){
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();
        String price = etPrice.getText().toString();
        String image = etImage.getText().toString();
        boolean stock = etStock.isChecked();

        if (name.isEmpty() || desc.isEmpty() || price.isEmpty() || image.isEmpty()){
            Toast.makeText(AddProduct.this, "All Fields Required..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (id == 0){
            add(new Product(0,name,desc,Integer.parseInt(price),image,stock));
        }else {
            update(new Product(id,name,desc,Integer.parseInt(price),image,stock));
        }

    }

    private void update(Product product) {
        Call<Respo> call = apiInterface.updateProduct(product);
        call.enqueue(new Callback<Respo>() {
            @Override
            public void onResponse(@NonNull Call<Respo> call, @NonNull Response<Respo> response) {
                if (response.isSuccessful()){
                    Respo respo = response.body();
                    assert respo != null;
                    Log.i("TAG",respo.isError()+"");
                    Log.i("TAG",respo.getMessage());
                    Toast.makeText(AddProduct.this, respo.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onFailure(@NonNull Call<Respo> call, @NonNull Throwable t) {
                Toast.makeText(AddProduct.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void add(Product product){
        Call<Respo> call = apiInterface.addProduct(product);
        call.enqueue(new Callback<Respo>() {
            @Override
            public void onResponse(@NonNull Call<Respo> call, @NonNull Response<Respo> response) {
                if (response.isSuccessful()){
                    Respo respo = response.body();
                    assert respo != null;
                    Log.i("TAG",respo.isError()+"");
                    Log.i("TAG",respo.getMessage());
                    Toast.makeText(AddProduct.this, respo.getMessage(), Toast.LENGTH_SHORT).show();
                    clear();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Respo> call, @NonNull Throwable t) {
                Toast.makeText(AddProduct.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void clear(){
        etName.getText().clear();
        etPrice.getText().clear();
        etDesc.getText().clear();
        etImage.getText().clear();
        etStock.setChecked(false);
    }
}