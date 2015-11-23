package com.sergiosfilho.mundishopclient;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class ProductImageAsyncTask extends AsyncTask<Integer, String, Bitmap> {
    public IProductImageReceiver receiver;
    public Integer position = 0;

    public ProductImageAsyncTask(IProductImageReceiver _receiver, Integer _position){
        this.receiver = _receiver;
        this.position = _position;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap retVal = null;
        try {
            Integer productId = params[0];
            String url = Globals.API_BASE_ADDRESS + "getProductPhoto?productId=" + productId;
            HttpClient client = new DefaultHttpClient();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpGet httpPostRequestToken = new HttpGet(url);
            String resultJson = client.execute(httpPostRequestToken, responseHandler);
            String imageBase64 = new Gson().fromJson(resultJson, String.class);
            byte[] imgBytes = Base64.decode(imageBase64, Base64.DEFAULT);
            retVal = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        }
        catch (Exception ex) { }
        return retVal;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        receiver.onProductImageReturned(bitmap, this.position);
    }

    public interface IProductImageReceiver {
        public void onProductImageReturned(Bitmap photo, Integer position);
    }
}
