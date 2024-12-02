package com.bebetter.sistema.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bebetter.sistema.entities.LoginRequest;
import com.bebetter.sistema.entities.LoginResponse;
import com.bebetter.sistema.entities.Usuario;
import com.bebetter.sistema.services.UsuarioService;

@RestController
@RequestMapping(value = "/index")
public class UsuarioController {

	@Autowired
	private UsuarioService tb_usuario;

	// Endpoint para buscar todos os usuários
	@GetMapping
	public List<Usuario> findAll() {
		List<Usuario> listaUsuarios = tb_usuario.findAll();
		return listaUsuarios;
	}

	// Endpoint para buscar um usuário pelo ID
	@GetMapping(value = "/{id}")
	public Usuario findById(@PathVariable Long id) {
		return tb_usuario.findById(id);
	}

	// Endpoint para cadastro de usuário
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> insert(@RequestBody Usuario obj) {
		// Criptografa a senha antes de salvar
		Usuario usuarioSalvo = tb_usuario.insert(obj);
		return ResponseEntity.ok(usuarioSalvo); // Retorna o usuário salvo
	}

	// Endpoint para login de usuário
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		String email = loginRequest.getEmail(); // Campo genérico que pode ser CPF ou email
		String senha = loginRequest.getSenha(); // Senha do usuário

		Usuario usuario = null;
		// Verifica se o valor contém '@' e '.' para ser considerado um email
		if (email.contains("@") && email.contains(".")) {
			// Tentativa de autenticação por Email
			usuario = tb_usuario.autenticarPorEmail(email, senha);
		}

		if (usuario != null) {

			return ResponseEntity.ok(new LoginResponse(true, "Login bem-sucedido", usuario.getId(), usuario.getNome(),
					usuario.getEmail()));
		} else {
			// Retorna resposta em JSON com erro
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ResponseMessage(false, "Credenciais inválidas"));
		}
	}

	// Endpoint para excluir um usuário pelo ID
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		tb_usuario.delete(id);
	}
}
