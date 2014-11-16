package org.hello;

import android.app.Activity;
import android.app.DialogFragment;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.content.Context;
import android.widget.Toast;

import org.hello.model.usuario.UsuarioDao;

public class FormEditItemActivity extends Activity {

  private UsuarioDao usuario = new UsuarioDao(this);

  TextView textId;
  EditText text35;
  EditText text37;
  EditText text39;
  EditText text41;
  EditText text43;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.form_new_item_view);

    textId = (TextView) findViewById(R.id.widget_id);
    text35 = (EditText) findViewById(R.id.widget35);
    text37 = (EditText) findViewById(R.id.widget37);
    text39 = (EditText) findViewById(R.id.widget39);
    text41 = (EditText) findViewById(R.id.widget41);
    text43 = (EditText) findViewById(R.id.widget43);

    Intent intent = getIntent();
    String itemA = intent.getStringExtra("itemA");
    String itemB = intent.getStringExtra("itemB");

    Cursor u = usuario.findByFields(new String[] {"nome", "sobrenome"}, new String[] {itemA, itemB});
    startManagingCursor(u);

    System.out.println("u.getCount == "+u.getCount());
    System.out.println("u.getColumnCount == "+u.getColumnCount());

    if(u.getCount() > 0) {
      if(u.getColumnCount() > 0) {
        u.moveToFirst();
        fillForm(u.getString(0), u.getString(1), u.getString(2), u.getString(3), u.getString(4), u.getString(5));
      }
    }
  }

  public void submitForm(View view) {
    String id = textId.getText().toString();
    String nome = text35.getText().toString();
    String sobrenome = text37.getText().toString();
    String email = text39.getText().toString();
    String login = text41.getText().toString();
    String senha = text43.getText().toString();

    String form[] = {id, nome, sobrenome, email, login, senha};
    usuario.update(form);
  }

  public void fillForm(String u1, String u2, String u3, String u4, String u5, String u6) {
    textId.setText(u1);
    text35.setText(u2);
    text37.setText(u3);
    text39.setText(u4);
    text41.setText(u5);
    text43.setText(u6);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu items for use in the action bar
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu_edit_form, menu);
      return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle presses on the action bar items
      switch (item.getItemId()) {
          case R.id.action_remove_item:
              openRemoveItem();
              return true;
          default:
              return super.onOptionsItemSelected(item);
      }
  }

  public void openRemoveItem() {
    DialogFragment dialog = new DeleteItemFragment();
    dialog.show(getFragmentManager(), "Confirmação");
  }

  public void positiveClick() {
      // User touched the dialog's positive button
      String id = textId.getText().toString();
      String nome = text35.getText().toString();
      String sobrenome = text37.getText().toString();
      String email = text39.getText().toString();
      String login = text41.getText().toString();
      String senha = text43.getText().toString();

      String form[] = {id, nome, sobrenome, email, login, senha};
      usuario.delete(form);
  }

  public void negativeClick() {
      // User touched the dialog's negative button
      Context context = getApplicationContext();
      CharSequence text = "Operação cancelada";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
  }

}
