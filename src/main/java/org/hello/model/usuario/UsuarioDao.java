package org.hello.model.usuario;

import android.content.Context;

import org.hello.generic.Dao;

public class UsuarioDao extends Dao<Usuario>{
  public UsuarioDao(Context context) {
    super(Usuario.class, context);
  }
}
