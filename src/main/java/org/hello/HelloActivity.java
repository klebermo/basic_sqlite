package org.hello;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.database.Cursor;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;

import org.hello.model.usuario.UsuarioDao;

public class HelloActivity extends ListActivity {

  private UsuarioDao usuario = new UsuarioDao(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.custom_list_activity_view);

    Cursor mCursor = usuario.findAll();
    startManagingCursor(mCursor);

    ListAdapter adapter = new SimpleCursorAdapter(
        this,
        R.layout.two_line_list_item,
        mCursor,
        new String[] {"nome", "sobrenome"},
        new int[] {R.id.textA, R.id.textB});

    setListAdapter(adapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    TextView itemA = (TextView) v.findViewById(R.id.textA);
    TextView itemB = (TextView) v.findViewById(R.id.textB);
    Intent intent = new Intent(this, FormEditItemActivity.class);
    intent.putExtra("itemA", itemA.getText().toString());
    intent.putExtra("itemB", itemB.getText().toString());
    startActivity(intent);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu items for use in the action bar
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu, menu);
      return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle presses on the action bar items
      switch (item.getItemId()) {
          case R.id.action_search:
              openSearch();
              return true;
          case R.id.action_settings:
              openSettings();
              return true;
          case R.id.action_new_item:
              openNewItem();
              return true;
          default:
              return super.onOptionsItemSelected(item);
      }
  }

  public void openSearch() {
    //
  }

  public void openSettings() {
    //
  }

  public void openNewItem() {
    Intent intent = new Intent(this, FormNewItemActivity.class);
    startActivity(intent);
  }

}
