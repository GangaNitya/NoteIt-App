package com.ganga.noteitnow.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;

public class NotesDataSource {
	private static final String PREFKEY = "note";
	private SharedPreferences notePrefs;
	
	public NotesDataSource(Context context){
		notePrefs = context.getSharedPreferences(PREFKEY,context.MODE_PRIVATE);
		
	}
	
	
	public List<NoteItem> findAll(){
		Map<String,?> map = notePrefs.getAll();
		
		SortedSet<String> keys = new TreeSet<String>(map.keySet());
		
			List<NoteItem> notesList = new ArrayList<NoteItem>();
		
		for (String key : keys) {
			NoteItem note = new NoteItem();
			note.setKey(key);
			note.setText((String) map.get(key));	
			notesList.add(note);
		}
	
		//NoteItem note = NoteItem.getNew();
		//notesList.add(note);
		
		return notesList;		
		}

	public boolean update(NoteItem note){
		SharedPreferences.Editor editor = notePrefs.edit();
		editor.putString(note.getKey(),note.getText());
		editor.commit();
		return true;
	}
	public boolean remove(NoteItem note){
		if (notePrefs.contains(note.getKey())) {
			SharedPreferences.Editor editor = notePrefs.edit();
			editor.remove(note.getKey());
			editor.commit();
					}
		return true;
	}
	
}
