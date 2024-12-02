package com.bebetter.sistema.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bebetter.sistema.entities.Postagem;
import com.bebetter.sistema.entities.Usuario;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {
	List<Postagem> findByUsuario(Usuario usuario);
	
}
