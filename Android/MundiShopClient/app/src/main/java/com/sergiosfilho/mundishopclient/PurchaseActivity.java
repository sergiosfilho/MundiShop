package com.sergiosfilho.mundishopclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import java.net.URLEncoder;
import java.text.NumberFormat;
import businessentities.Purchase;

public class PurchaseActivity extends ActionBarActivity {
    Integer productId;
    Float productPrice;
    String productName;

    Button btnPurchase;
    EditText tbxName;
    EditText tbxCreditCardNumber;
    EditText tbxCvv;
    EditText tbxCreditCardExpYear;
    Spinner cmbMonth;
    RadioGroup rgFlag;
    TextView tvProductName;
    TextView tvProductPrice;
    TextView tvErrorFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);
        initComponentsReferences();
        fillView(getIntent());
        initListeners();
    }

    private void initComponentsReferences(){
        btnPurchase = (Button)findViewById(R.id.btnPurchase);
        tbxName=(EditText)findViewById(R.id.tbxName);
        tbxCreditCardNumber = (EditText)findViewById(R.id.tbxCreditCardNumber);
        tbxCvv = (EditText)findViewById(R.id.tbxCvv);
        tbxCreditCardExpYear = (EditText)findViewById(R.id.tbxCreditCardExpYear);
        cmbMonth = (Spinner)findViewById(R.id.cmbMonth);
        rgFlag = (RadioGroup)findViewById(R.id.rgFlag);
        tvProductName = (TextView)findViewById(R.id.tvProductName);
        tvProductPrice = (TextView)findViewById(R.id.tvProductPrice);
        tvErrorFeedback = (TextView)findViewById(R.id.tvErrorFeedback);
    }

    private void fillView(Intent intent){
        productId = intent.getIntExtra("productId", 0);
        productPrice = intent.getFloatExtra("productPrice", 0f);
        productName = intent.getStringExtra("productName");
        tvProductName.setText(productName);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        tvProductPrice.setText(format.format(productPrice));
    }

    private void initListeners(){
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Purchase purchase = new Purchase();
            try{
                purchase.CreditCardExpirationYear = Integer.parseInt(tbxCreditCardExpYear.getText().toString());
            }catch (Exception ex){
                Toast.makeText(PurchaseActivity.this, "Ano de vencimento inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            purchase.ProductId = productId;
            purchase.AmountPaid = productPrice;
            purchase.CreditCardCvv = tbxCvv.getText().toString();
            purchase.CreditCardNumber = tbxCreditCardNumber.getText().toString();
            purchase.CustomerName = tbxName.getText().toString();
            purchase.CreditCardExpirationMonth = cmbMonth.getSelectedItemPosition() + 1;
            int radioButtonID = rgFlag.getCheckedRadioButtonId();
            View radioButton = rgFlag.findViewById(radioButtonID);
            purchase.CreditCardFlag = rgFlag.indexOfChild(radioButton);
            PurchaseAsyncTask atPurchase = new PurchaseAsyncTask(PurchaseActivity.this, productName);
            atPurchase.execute(purchase);
            }
        });
    }

    public void onPurchaseRequestReturned(int resultCode){
        if(resultCode == 0){
            Intent historyIntent = new Intent(PurchaseActivity.this, PurchaseHistoryActivity.class);
            PurchaseActivity.this.startActivity(historyIntent);
        }
        else{
            tvErrorFeedback.setVisibility(View.VISIBLE);
            switch (resultCode){
                case 1:
                    tvErrorFeedback.setText("Data de validade do cartão inválida.");
                    break;
                case 2:
                    tvErrorFeedback.setText("CVV do cartão inválido.");
                    break;
                case 3:
                    tvErrorFeedback.setText("Produto inválido.");
                    break;
                case 4:
                    tvErrorFeedback.setText("Valor de pagamento incorreto.");
                    break;
                case 5:
                    tvErrorFeedback.setText("Não foi possível realizar a operação, tente novamente mais tarde.");
                    break;
            }
        }
    }

    public class PurchaseAsyncTask extends AsyncTask<Purchase, String, Integer>{
        PurchaseActivity view;
        String productName;
        ProgressDialog pleaseWait;

        public PurchaseAsyncTask(PurchaseActivity _view, String _productName){
            this.productName = _productName;
            this.view = _view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pleaseWait = new ProgressDialog(view);
            pleaseWait.setTitle("Aguarde");
            pleaseWait.setMessage("Efetuando pagamento...");
            pleaseWait.show();
        }

        @Override
        protected Integer doInBackground(Purchase... params) {
            Integer retVal = -1;
            try {
                Purchase purchase = params[0];
                String purchaseJson = new Gson().toJson(purchase);
                String url = Globals.API_BASE_ADDRESS + "DoPurchase?purchaseRequest=" + URLEncoder.encode(purchaseJson, "UTF-8");
                HttpClient client = new DefaultHttpClient();
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                HttpGet httpPostRequestToken = new HttpGet(url);
                String resultJson = client.execute(httpPostRequestToken, responseHandler);
                retVal = new Gson().fromJson(resultJson, Integer.TYPE);
                if(retVal == 0){
                    LocalDBHelper dbHelper = new LocalDBHelper(view);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    dbHelper.insertPurchase(db,purchase, productName);
                }
            }catch (Exception ex){
                retVal = 5;
            }
            // 0=Success, 1=InvalidExpirationDate, 2=InvalidCvv, 3=InvalidProduct, 4=InvalidPaymentAmount
            // 5=UnexpectedException
            return retVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            pleaseWait.dismiss();
            view.onPurchaseRequestReturned(result);
        }
    }
}
