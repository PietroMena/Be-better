package com.bebetter.sistema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebetter.sistema.entities.Usuario;
import com.bebetter.sistema.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository tb_usuario;
    

    // Buscar todos os usuários
    public List<Usuario> findAll() {
        return tb_usuario.findAll();
    }

    // Buscar usuário por ID
    public Usuario findById(Long id) {
        Optional<Usuario> usuario = tb_usuario.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Buscar usuário por e-mail
    public Usuario findByEmail(String email) {
        return tb_usuario.findByEmail(email);
    }

    // Inserir um novo usuário com senha criptografada
    public Usuario insert(Usuario obj) {
        return tb_usuario.save(obj);
    }
    	
    // Deletar um usuário por ID
    public void delete(Long id) {
        tb_usuario.deleteById(id);
    }

    // Verificar login (comparar a senha fornecida com a armazenada)
    public boolean verificarLogin(String email, String senha) {
        Usuario usuario = tb_usuario.findByEmail(email);
        if (usuario != null) {
            return true; // Senha válida
        }
        return false; // Senha ou usuário inválido
    }
    

    public Usuario autenticarPorEmail(String email, String senha) {
        Usuario usuario = tb_usuario.findByEmail(email);  // Método que busca o usuário pelo email

        // Verifica se o usuário foi encontrado e a senha está correta
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;  // Retorna null se a autenticação falhar
    }
    
    
    
    
}
