package com.example.dessertorder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class IndexPageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.logoface);
		new InitTask().execute(1);
	}
	private class InitTask extends AsyncTask<Integer, Integer, Long>{
		@Override
		protected Long doInBackground(Integer... params) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			return (long)1;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(Long result) {
			Intent intent = new Intent();
			intent.setClass(IndexPageActivity.this, LoginActivity.class);
			startActivity(intent);
			super.onPostExecute(result);
			
			IndexPageActivity.this.finish();
		}
	}
}
