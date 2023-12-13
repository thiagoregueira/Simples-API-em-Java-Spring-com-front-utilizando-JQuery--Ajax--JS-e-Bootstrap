package br.com.springrestapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.springrestapi.model.Usuario;
import br.com.springrestapi.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // listar todos os usuarios
    @GetMapping(value = "listartodos")
    @ResponseBody
    public ResponseEntity<List<Usuario>> findAll() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return ResponseEntity.ok(usuarios);
    }

    // salvar um novo usuario
    @PostMapping(value = "salvar")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    // deletar um usuario retornando uma mensagem confirmando a deleção
    @DeleteMapping(value = "deletar")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long id) {
        usuarioRepository.deleteById(id);
        return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);
    }

    // buscar por id e retornar o usuario
    @GetMapping(value = "buscarporid")
    @ResponseBody
    public ResponseEntity<Usuario> findById(@RequestParam(value = "id") Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // atualizar um usuario
    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
        Optional<Usuario> existingUsuario = usuarioRepository.findById(usuario.getId());
        if (existingUsuario.isPresent()) {
            Usuario updatedUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // buscar por parte do nome e retornar os usuarios
    @GetMapping(value = "buscarpornome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> findByNome(@RequestParam(value = "nome") String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        return ResponseEntity.ok(usuarios);
    }

}
