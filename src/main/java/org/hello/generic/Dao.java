package org.hello.generic;

import java.util.List;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import org.hello.config.DataHelper;

public class Dao<E extends Model> {

  private Class<? extends Model> clazz;

  private OpenHelper openHelper;

  public Dao(Class<? extends Model> clazz, Context context) {
    this.clazz = clazz;
    openHelper = new OpenHelper(clazz, context, DataHelper.DATABASE_NAME, DataHelper.DATABASE_VERSION);
  }

  public void insert(String[] data) {
    try {
      Model object = clazz.newInstance();
      List<String> lista = object.getFields();

      List<String> campos = new ArrayList<String>();
      for(int i=1; i<lista.size(); i++)
        campos.add(lista.get(i));

      List<String> values = new ArrayList<String>();
      for(int i=0; i<campos.size(); i++)
        values.add("?");

      String scampos = campos.toString().replace("[", "(").replace("]", ")");
      String svalues = values.toString().replace("[", "(").replace("]", ")");
      String INSERT = "insert into " + clazz.getSimpleName() + scampos + " values "+ svalues;
      System.out.println("===== INSERT -> "+INSERT);

      SQLiteDatabase db = openHelper.getWritableDatabase();
      SQLiteStatement insertStmt = db.compileStatement(INSERT);

      for(int i=0; i<data.length; i++)
        insertStmt.bindString(i+1, data[i]);

      insertStmt.executeInsert();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void update(String[] data) {
    try {
      Model object = clazz.newInstance();
      List<String> campos = object.getFields();
      List<String> pares = new ArrayList<String>();

      for(int i=0; i<campos.size(); i++)
        pares.add(campos.get(i)+" = ?");

      String spares = pares.toString().replace("[", "").replace("]", "").replace(",", " and ");
      String UPDATE = "update " + clazz.getSimpleName() + " set " + spares + " where _id='" + data[0] + "'";
      System.out.println("===== UPDATE -> "+UPDATE);

      SQLiteDatabase db = openHelper.getWritableDatabase();
      SQLiteStatement updateStmt = db.compileStatement(UPDATE);

      for(int i=0; i<data.length; i++) {
        updateStmt.bindString(i+1, data[i]);
      }

      updateStmt.executeUpdateDelete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void delete(String[] data) {
    try {
      Model object = clazz.newInstance();
      String DELETE = "delete from " + clazz.getSimpleName() + " where _id='" + data[0] + "'";
      System.out.println("===== DELETE -> "+DELETE);

      SQLiteDatabase db = openHelper.getWritableDatabase();
      SQLiteStatement deleteStmt = db.compileStatement(DELETE);

      deleteStmt.executeUpdateDelete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public E findById(int id) {
    SQLiteDatabase db = openHelper.getReadableDatabase();
    Cursor cursor = db.query(clazz.getSimpleName(), null, "_id = ?", new String[] {String.valueOf(id)}, null, null, null, null);

    Model object;
    try {
      object = clazz.newInstance();
      for(int i=0; i<cursor.getColumnCount(); i++) {
        object.setValue(i, cursor.getString(i));
      }
    } catch (Exception e) {
      object = null;
    }

    return (E) object;
  }

  public Cursor findByField(String key, String value) {
    SQLiteDatabase db = openHelper.getReadableDatabase();
    return db.query(clazz.getSimpleName(), null, key + " = ?", new String[] {value}, null, null, null, null);
  }

  public Cursor findByFields(String[] key, String[] value) {
    List<String> keys = new ArrayList<String>();
    for(int i=0; i<key.length; i++) {
      keys.add(key[i] + " = ?");
    }
    String attr_key = keys.toString().replace(",", " and ").replace("[", "").replace("]", "");

    SQLiteDatabase db = openHelper.getReadableDatabase();
    return db.query(clazz.getSimpleName(), null, attr_key, value, null, null, null, null);
  }

  public Cursor findAll() {
    SQLiteDatabase db = openHelper.getReadableDatabase();
    return db.query(clazz.getSimpleName(), null, null, null, null, null, null, null);
  }

  private class OpenHelper extends SQLiteOpenHelper {

    private Class<? extends Model> clazz;

    public OpenHelper(Class<? extends Model> clazz, Context context, String database_name, int database_version) {
      super(context, database_name, null, database_version);
      this.clazz = clazz;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
  		try {
  			Model object = clazz.newInstance();
        String CREATE = "CREATE TABLE " + this.getClazz().getSimpleName() + object.dbFields().toString().replace("[", "(").replace("]", ")");
        db.execSQL( CREATE );
  		} catch (InstantiationException | IllegalAccessException e) {
  			e.printStackTrace();
  		}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + this.getClazz().getSimpleName());
      onCreate(db);
    }

    public Class<? extends Model> getClazz() {
      return clazz;
    }

    public void setClazz(Class<? extends Model> clazz) {
      this.clazz = clazz;
    }

  }

}
