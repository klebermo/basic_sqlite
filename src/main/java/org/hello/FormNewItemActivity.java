package org.hello;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;

import org.hello.model.usuario.UsuarioDao;

public class FormNewItemActivity extends Activity {

  private UsuarioDao usuario = new UsuarioDao(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.form_new_item_view);
  }

  public void submitForm(View view) {
    EditText text35 = (EditText) findViewById(R.id.widget35);
    String nome = text35.getText().toString();

    EditText text37 = (EditText) findViewById(R.id.widget37);
    String sobrenome = text37.getText().toString();

    EditText text39 = (EditText) findViewById(R.id.widget39);
    String email = text39.getText().toString();

    EditText text41 = (EditText) findViewById(R.id.widget41);
    String login = text41.getText().toString();

    EditText text43 = (EditText) findViewById(R.id.widget43);
    String senha = text43.getText().toString();

    String form[] = {nome, sobrenome, email, login, senha};
    usuario.insert(form);

    text35.setText("");
    text37.setText("");
    text39.setText("");
    text41.setText("");
    text43.setText("");
  }

}
