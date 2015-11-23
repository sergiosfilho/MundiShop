package com.sergiosfilho.mundishopclient;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

public class PurchaseHistoryActivity extends ActionBarActivity {
    ListView lvHistory;
    TextView tvTopMessage;
    List<LocalDBHelper.PurchaseHistoryItem> historyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchasehistory);
        initComponentsReferences();
        HistoryAsyncTask atHistory = new HistoryAsyncTask(this);
        atHistory.execute();
    }

    private void initComponentsReferences(){
        lvHistory = (ListView)findViewById(R.id.lvHistory);
        tvTopMessage = (TextView)findViewById(R.id.tvTopMessage);
    }

    public void onHistoryReturned(List<LocalDBHelper.PurchaseHistoryItem> _historyItems){
        if(_historyItems != null && _historyItems.size() > 0){
            this.historyItems = _historyItems;
            HistoryAdapter newAdapter = new HistoryAdapter();
            lvHistory.setAdapter(newAdapter);
        }
        else{
            tvTopMessage.setVisibility(View.VISIBLE);
            lvHistory.setVisibility(View.GONE);
        }
    }

    public class HistoryAsyncTask extends AsyncTask<String, String, List<LocalDBHelper.PurchaseHistoryItem>> {
        PurchaseHistoryActivity view;

        public HistoryAsyncTask(PurchaseHistoryActivity _view){
            this.view = _view;
        }

        @Override
        protected List<LocalDBHelper.PurchaseHistoryItem> doInBackground(String... params) {
            LocalDBHelper dbHelper = new LocalDBHelper(view);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            List<LocalDBHelper.PurchaseHistoryItem> historyItems = dbHelper.retrieveAllPurchases(db);
            return historyItems;
        }

        @Override
        protected void onPostExecute(List<LocalDBHelper.PurchaseHistoryItem> result) {
            super.onPostExecute(result);
            onHistoryReturned(result);
        }
    }

    public class HistoryAdapter extends ArrayAdapter<LocalDBHelper.PurchaseHistoryItem> {
        public HistoryAdapter() {
            super(PurchaseHistoryActivity.this, R.layout.historyitem, historyItems);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.historyitem, parent, false);

            LocalDBHelper.PurchaseHistoryItem historyItem = historyItems.get(position);

            TextView tvProductName = (TextView)itemView.findViewById(R.id.tvHistoryItemName);
            tvProductName.setText(historyItem.ProductName);

            TextView tvHistoryItemPrice = (TextView)itemView.findViewById(R.id.tvHistoryItemPrice);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            tvHistoryItemPrice.setText(format.format(historyItem.Purchase.AmountPaid));

            TextView tvHistoryItemCustomer = (TextView)itemView.findViewById(R.id.tvHistoryItemCustomer);
            tvHistoryItemCustomer.setText(historyItem.Purchase.CustomerName);

            TextView tvHistoryItemCCFlag = (TextView)itemView.findViewById(R.id.tvHistoryItemCCFlag);
            switch(historyItem.Purchase.CreditCardFlag){
                case 0:
                    tvHistoryItemCCFlag.setText("Visa");
                    break;
                case 1:
                    tvHistoryItemCCFlag.setText("Master Card");
                    break;
                case 2:
                    tvHistoryItemCCFlag.setText("American Express");
                    break;
                default:
                    tvHistoryItemCCFlag.setText("Não conhecida");
                    break;
            }

            return itemView;
        }
    }
}
