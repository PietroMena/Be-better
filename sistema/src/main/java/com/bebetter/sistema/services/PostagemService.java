package com.bebetter.sistema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebetter.sistema.entities.Postagem;
import com.bebetter.sistema.entities.Usuario;
import com.bebetter.sistema.repositories.PostagemRepository;

import jakarta.persistence.EntityNotFoundException;



@Service
public class PostagemService {
	@Autowired
	private PostagemRepository tb_postagem;

	public List<Postagem> findAll() {
		List<Postagem> listaPostagens = tb_postagem.findAll();
		return listaPostagens;
	}

	public Postagem findById(Long id) {
	    return tb_postagem.findById(id).orElseThrow(() ->
	        new EntityNotFoundException("Postagem não encontrada com o id: " + id));
	}
	
	public List<Postagem> findByUsuario(Usuario usuario) {
	    return tb_postagem.findByUsuario(usuario);
	}

	public Postagem insert(Postagem obj) {
	    if (obj == null || obj.getConteudo() == null || obj.getData_postagem() == null) {
	        throw new IllegalArgumentException("Dados inválidos: o conteúdo e a data são obrigatórios.");
	    }
	    return tb_postagem.save(obj);
	}

	public void delete(Long id) {
		tb_postagem.deleteById(id);
	}
	
	

}
