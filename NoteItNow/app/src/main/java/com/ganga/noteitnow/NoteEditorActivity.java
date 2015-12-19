package com.ganga.noteitnow;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


import com.ganga.noteitnow.data.NoteItem;

public class NoteEditorActivity extends Activity {
	private NoteItem note;
	private EditText et;
	private TextView tv;
	private TextView tv2;
	String liveData;
	int characterCount;
	int wordCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_edit);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		et = (EditText) findViewById(R.id.noteText);
		tv = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		liveData = new String();
		
		note = new NoteItem();
		Intent intent = this.getIntent();
		note.setKey(intent.getStringExtra("key"));
		note.setText(intent.getStringExtra("text"));
				
		et.setText(note.getText());
		et.setSelection(note.getText().length());
		
		characterCount = countCharacters(note.getText().toString());
		String data = note.getText().toString();
		tv.setText("Characters: "+characterCount);
		tv2.setText("Words: "+countWords(data));
		et.requestFocus();
		
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				MyTask task;
				task = new MyTask();
			 task.execute("Characters: "+countCharacters(et.getText().toString()),"Words: "+countWords(et.getText().toString()));	
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});		
	}
	private void saveAndFinish(){
		
		Intent intent = new Intent();
		intent.putExtra("key", note.getKey());
		intent.putExtra("text",et.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.note_editor_menu, menu);
	        return true;
	}
	
@Override
public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home || item.getItemId() == R.id.action_save) {
			saveAndFinish();
		}
		
		
		return false;
}
	@Override
	public void onBackPressed() {
			saveAndFinish();
	}
	public void updateCountDisplay(String s1,String s2) {
		tv.setText(s1);
		tv2.setText(s2);
		
	}
	
	private int countWords(String data){
		int wc = 0;
		boolean word = false;
		int endOfLine = data.length() - 1;
		for (int i = 0; i < data.length(); i++) {
			if(Character.isLetterOrDigit(data.charAt(i)) && i!=endOfLine)
				word = true;
			else if(!Character.isLetterOrDigit(data.charAt(i)) && word)
			{
				wc += 1;
				word = false;
			}
			else if(Character.isLetterOrDigit(data.charAt(i)) && (i == endOfLine))
				wc += 1;
					
		}
		return wc;
		
	}
	private int countCharacters(String data){
		int cc = 0;
		for (int i = 0; i < data.length(); i++) {
			
			if(Character.isLetterOrDigit(data.charAt(i)))
				cc +=1;
		}
		return cc;
		
	}
	
	private class MyTask extends AsyncTask<String, String, String[]>{

		
		@Override
		protected String[] doInBackground(String... params) {
		
			return params;
			
		}
		
		@Override
		protected void onPostExecute(String[] result) {
		 			
			updateCountDisplay(result[0],result[1]);
			
		}
		
	}

	
}
