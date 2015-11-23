package com.sergiosfilho.mundishopclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;

//TODO: substituir todos os textos direto no xml da tela por chaves de dicionário
//TODO: utilizar parcel para recuperar dados de views destruídas ao girar o dispositivo
public class ProductActivity extends ActionBarActivity implements ProductImageAsyncTask.IProductImageReceiver {
    Integer productId = 0;
    Float productPrice = 0f;
    TextView tvProductName;
    TextView tvProductPrice;
    TextView tvProductDescription;
    ImageView ivProductPhoto;
    Button btnPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        initComponentsReferences();
        initListeners();
        fillView(getIntent());
    }

    private void initComponentsReferences(){
        tvProductName = (TextView)findViewById(R.id.tvProductName);
        tvProductPrice = (TextView)findViewById(R.id.tvProductPrice);
        tvProductDescription = (TextView)findViewById(R.id.tvProductDescription);
        ivProductPhoto = (ImageView)findViewById(R.id.ivProductPhoto);
        btnPurchase = (Button)findViewById(R.id.btnPurchase);
    }

    private void initListeners(){
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseIntent = new Intent(ProductActivity.this, PurchaseActivity.class);
                purchaseIntent.putExtra("productId", productId);
                purchaseIntent.putExtra("productName", tvProductName.getText().toString());
                purchaseIntent.putExtra("productPrice", productPrice);
                ProductActivity.this.startActivity(purchaseIntent);
            }
        });
    }

    private void fillView(Intent intent){
        tvProductName.setText(intent.getStringExtra("productName"));
        tvProductDescription.setText(intent.getStringExtra("productDescription"));

        productPrice = intent.getFloatExtra("productPrice", 0);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        tvProductPrice.setText(format.format(productPrice));

        productId = intent.getIntExtra("productId", 0);
        ProductImageAsyncTask atGetPhoto = new ProductImageAsyncTask(this, 0);
        atGetPhoto.execute(productId);
    }

    public void onProductImageReturned(Bitmap photo, Integer position){
       ivProductPhoto.setImageBitmap(photo);
    }

}
