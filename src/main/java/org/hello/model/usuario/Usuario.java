package org.hello.model.usuario;

import org.hello.generic.Model;
import org.hello.config.Order;

public class Usuario extends Model {
  @Order(value = 1)
  private Integer id;

  @Order(value = 2)
  private String nome;

  @Order(value = 3)
  private String sobrenome;

  @Order(value = 4)
  private String email;

  @Order(value = 5)
  private String login;

  @Order(value = 6)
  private String senha;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSobrenome() {
    return sobrenome;
  }

  public void setSobrenome(String sobrenome) {
    this.sobrenome = sobrenome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  @Override
  public String toString() {
    return nome + " " + sobrenome;
  }

}
