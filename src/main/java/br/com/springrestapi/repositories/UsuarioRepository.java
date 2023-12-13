package br.com.springrestapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.springrestapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // buscar por parte do nome e retornar os usuarios
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
