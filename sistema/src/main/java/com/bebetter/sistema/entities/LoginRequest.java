package com.bebetter.sistema.entities;

public class LoginRequest {
	private String email; // Campo genérico para CPF ou email
	private String senha; // Senha

	// Getters e setters
	public String getEmail() {
		return email;
	}

	public void setCpfOuEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
