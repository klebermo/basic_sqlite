package org.hello.generic;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.hello.config.Order;

public abstract class Model {
  public abstract String toString();

  public List<String> getFields() {
    Field fields[] = this.getClass().getDeclaredFields();
    String lista[] = new String[fields.length];

    for(int i=0; i<fields.length; i++) {
      String name = fields[i].getName();
      int pos = fields[i].getAnnotation(Order.class).value();

      if(name.equals("id"))
        lista[pos-1] = "_"+fields[i].getName();
      else
        lista[pos-1] = fields[i].getName();
    }

    return Arrays.asList(lista);
  }

  public List<String> getTypes() {
    Field fields[] = this.getClass().getDeclaredFields();
    String lista[] = new String[fields.length];

    for(int i=0; i<fields.length; i++) {
      int pos = fields[i].getAnnotation(Order.class).value();
      lista[pos-1] = fields[i].getType().getSimpleName();
    }

    return Arrays.asList(lista);
  }

  public List<String> dbFields() {
    List<String> lista = new ArrayList<String>();

    List<String> name = this.getFields();
    List<String> type = this.getTypes();
    for(int i=0; i<name.size(); i++) {
      if(name.get(i).equals("id")) {
        lista.add(new String("_id INTEGER PRIMARY KEY"));
      } else {
        if(type.get(i).equals("String"))
          lista.add(new String(name.get(i) + "  VARCHAR"));
        else
          lista.add(new String(name.get(i)+" "+type.get(i).toUpperCase()));
      }
    }

    return lista;
  }

  public Object getValue(int position) {
    List<Method> all_methods = Arrays.asList(this.getClass().getMethods());
    List<Method> getter_methods = new ArrayList<Method>();

    for(int i=0; i<all_methods.size(); i++) {
      if(all_methods.get(i).getName().substring(0, 3).equals("get"))
        getter_methods.add(all_methods.get(i));
    }

    try {
      return getter_methods.get(position).invoke(this, null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void setValue(int position, Object value) {
    List<Method> all_methods = Arrays.asList(this.getClass().getMethods());
    List<Method> setter_methods = new ArrayList<Method>();

    for(int i=0; i<all_methods.size(); i++) {
      if(all_methods.get(i).getName().substring(0, 3).equals("set"))
        setter_methods.add(all_methods.get(i));
    }

    try {
      setter_methods.get(position).invoke(this, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
