package com.sergiosfilho.mundishopclient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.NumberFormat;

import businessentities.Product;


public class MainActivity extends ActionBarActivity implements ProductImageAsyncTask.IProductImageReceiver {
    Product[] products;
    ListView lvProducts;
    TextView tvTopMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitComponentsReferences();
        initListeners();
        ProductsAsyncTask atGetProducts = new ProductsAsyncTask(this);
        atGetProducts.execute();
    }



    private void initListeners(){
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = products[position];
                Intent productIntent = new Intent(MainActivity.this, ProductActivity.class);
                productIntent.putExtra("productId", product.Id);
                productIntent.putExtra("productName", product.Name);
                productIntent.putExtra("productDescription", product.Description);
                productIntent.putExtra("productPrice", product.Price);
                MainActivity.this.startActivity(productIntent);
            }
        });
    }

    public void onProductsReturned(ProductsAsyncTask.ProductsAsyncTaskResult result){
        if(result.Success){
            this.products = result.Products;
            ProductsAdapter newAdapter = new ProductsAdapter();
            lvProducts.setAdapter(newAdapter);
            getProductImage();
        }
        else{
            lvProducts.setVisibility(View.GONE);
            tvTopMessage.setVisibility(View.VISIBLE);
            tvTopMessage.setText("Erro carregando produtos, tente novamente mais tarde.");
        }
    }

    int getProductImageIndex = 0;
    private void getProductImage(){
        if(getProductImageIndex < products.length){
            ProductImageAsyncTask atGetPhoto = new ProductImageAsyncTask(this, getProductImageIndex);
            atGetPhoto.execute(products[getProductImageIndex].Id);
        }
    }

    public void onProductImageReturned(Bitmap photo, Integer position){
        products[position].Photo = photo;
        ProductsAdapter adapter = (ProductsAdapter)lvProducts.getAdapter();
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
        getProductImageIndex++;
        getProductImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                Intent historyIntent = new Intent(MainActivity.this, PurchaseHistoryActivity.class);
                MainActivity.this.startActivity(historyIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void InitComponentsReferences(){
        lvProducts = (ListView)findViewById(R.id.lvProducts);
        tvTopMessage = (TextView)findViewById(R.id.lblTopMessage);
    }

    public class ProductsAdapter extends ArrayAdapter<Product> {
        public ProductsAdapter() {
            super(MainActivity.this, R.layout.productitem, products);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.productitem, parent, false);

            Product product = products[position];

            TextView tvProductName = (TextView)itemView.findViewById(R.id.tvProductItemName);
            tvProductName.setText(product.Name);

            TextView tvProductItemDescription = (TextView)itemView.findViewById(R.id.tvProductItemDescription);
            tvProductItemDescription.setText(product.Description);

            TextView tvProductItemPrice = (TextView)itemView.findViewById(R.id.tvProductItemPrice);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            tvProductItemPrice.setText(format.format(product.Price));

            ImageView imgContactItem = (ImageView)itemView.findViewById(R.id.ivProductItemPhoto);
            if(product.Photo != null) { imgContactItem.setImageBitmap(product.Photo); }
            else { imgContactItem.setImageResource(R.drawable.no_image_available); }

            return itemView;
        }
    }

    public class ProductsAsyncTask extends AsyncTask<String, String, ProductsAsyncTask.ProductsAsyncTaskResult>{
        public MainActivity view;

        public ProductsAsyncTask(MainActivity _view){
            this.view = _view;
        }

        @Override
        protected ProductsAsyncTaskResult doInBackground(String... params) {
            ProductsAsyncTaskResult retVal = new ProductsAsyncTaskResult();
            try {
                String url = Globals.API_BASE_ADDRESS + "getAllProducts";
                HttpClient client = new DefaultHttpClient();
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                HttpGet httpPostRequestToken = new HttpGet(url);
                String resultJson = client.execute(httpPostRequestToken, responseHandler);
                retVal.Products = new Gson().fromJson(resultJson, Product[].class);
                retVal.Success = true;
            }
            catch(Exception ex){
                retVal.Success = false;
                retVal.ErrorMessage = ex.toString();
            }
            return retVal;
        }

        @Override
        protected void onPostExecute(ProductsAsyncTaskResult productsAsyncTaskResult) {
            super.onPostExecute(productsAsyncTaskResult);
            view.onProductsReturned(productsAsyncTaskResult);
        }

        public class ProductsAsyncTaskResult {
            boolean Success;
            String ErrorMessage;
            Product[] Products;
        }
    }


}


