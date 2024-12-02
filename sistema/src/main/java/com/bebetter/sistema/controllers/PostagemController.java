package com.bebetter.sistema.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bebetter.sistema.entities.Postagem;
import com.bebetter.sistema.entities.Usuario;
import com.bebetter.sistema.services.PostagemService;



@CrossOrigin(origins = "*") // Permitir requisições de qualquer origem
@RestController
@RequestMapping(value = "/postagem")
public class PostagemController {
	
	@Autowired
	private PostagemService tb_postagem;
	
	@GetMapping
	public List<Postagem> findAll(){
		List<Postagem> listaPostagens = tb_postagem.findAll();
		return listaPostagens;
	}
	
	@GetMapping(value = "/{id}")
	public Postagem findById(@PathVariable Long id){
		return tb_postagem.findById(id);
		
	}
	
	@GetMapping(value = "/usuario/{id}")
	public List<Postagem> findPostagemPorUsuario(@PathVariable("id") Usuario usuario){
		return tb_postagem.findByUsuario(usuario);
	}
	
	@PostMapping
	public Postagem insert(@RequestBody Postagem obj) {
		return obj = tb_postagem.insert(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
	    tb_postagem.delete(id);
	    return ResponseEntity.ok("Postagem excluída com sucesso!");
	}

}
