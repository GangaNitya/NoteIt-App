package com.ganga.noteitnow;

import java.util.List;

import com.ganga.noteitnow.data.NoteItem;
import com.ganga.noteitnow.data.NotesDataSource;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity{
	private static final int EDITOR_ACTIVITY_REQUEST = 1001;
	private static final int DELETE_MENU_ID = 1002;
	private int currentNoteId;
	private NotesDataSource dataSource;
	private  List<NoteItem> notesList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerForContextMenu(getListView());

		dataSource = new NotesDataSource(this);

		refreshDisplay();
	}


	private void refreshDisplay() {
		notesList = dataSource.findAll();
		ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this,R.layout.list_item_layout,notesList);
		setListAdapter(adapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId() == R.id.action_create){

			createNote();
		}

		return false;
	}


	private void createNote() {
		NoteItem note = NoteItem.getNew();
		Intent intent = new Intent(this,NoteEditorActivity.class);
		intent.putExtra("key",note.getKey());
		intent.putExtra("text", note.getText());
		startActivityForResult(intent,EDITOR_ACTIVITY_REQUEST);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		currentNoteId = (int) info.id;
		menu.add(0, DELETE_MENU_ID, 0, "Delete Note");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getItemId() == DELETE_MENU_ID){

			NoteItem note = new NoteItem();
			note = notesList.get(currentNoteId);
			dataSource.remove(note);
			refreshDisplay();
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		NoteItem note = new NoteItem();
		note = notesList.get(position);
		Intent intent = new Intent(this,NoteEditorActivity.class);
		intent.putExtra("key",note.getKey());
		intent.putExtra("text", note.getText());
		startActivityForResult(intent,EDITOR_ACTIVITY_REQUEST);

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK){
			NoteItem note = new NoteItem();
			note.setKey(data.getStringExtra("key"));
			note.setText(data.getStringExtra("text"));
			dataSource.update(note);
			refreshDisplay();

		}

	}
}