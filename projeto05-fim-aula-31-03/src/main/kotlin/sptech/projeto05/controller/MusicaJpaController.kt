package sptech.projeto05.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sptech.projeto05.entity.Usuario
import sptech.projeto05.repository.UsuarioRepository

@RestController

@RequestMapping("/usuarios")

class UsuarioController(val repository: UsuarioRepository) {


    @GetMapping
    fun listar(): ResponseEntity<List<Usuario>> {
        val usuarios = repository.findAll()

        return if (usuarios.isEmpty()) ResponseEntity.noContent().build()
        else ResponseEntity.ok(usuarios)
    }

    @GetMapping("/{id}")

    fun buscar(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuario = repository.findById(id)
        return if (usuario.isPresent) ResponseEntity.ok(usuario.get())
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun cadastrar(@RequestBody @Valid novoUsuario: Usuario): ResponseEntity<Usuario> {

        val salvo = repository.save(novoUsuario)
        return ResponseEntity.status(201).body(salvo)

    }

    @PutMapping("/{id}")

    fun atualizar(@PathVariable id: Int, @RequestBody @Valid usuarioAtualizado: Usuario): ResponseEntity<Usuario> {

        if (!repository.existsById(id)) return ResponseEntity.notFound().build()

        usuarioAtualizado.idUsuario = id
        val atualizado = repository.save(usuarioAtualizado)
        return ResponseEntity.ok(atualizado)

    }

    @PatchMapping("/recuperar-senha")

    fun recuperarSenhaPorId(

        @RequestParam id: Int,
        @RequestParam novaSenha: String,
        @RequestParam repetirSenha: String

    ): ResponseEntity<String> {

        if (novaSenha != repetirSenha) {
            return ResponseEntity.badRequest().body("As senhas não coincidem")

        }

        val usuarioOptional = repository.findById(id)
        if (usuarioOptional.isEmpty) return ResponseEntity.notFound().build()

        val usuario = usuarioOptional.get()
        usuario.senha = novaSenha
        repository.save(usuario)

        return ResponseEntity.ok("Senha atualizada com sucesso!")

    }

    @PatchMapping("/{id}/status")

    fun alterarStatusUsuario(
        @PathVariable id: Int,
        @RequestParam isAtivo: Boolean

    ): ResponseEntity<String> {

        val usuarioOptional = repository.findById(id)
        if (usuarioOptional.isEmpty) return ResponseEntity.notFound().build()

        val usuario = usuarioOptional.get()

        if (usuario.isAtivo == isAtivo) {
            val status = if (isAtivo) "ativo" else "inativo"
            return ResponseEntity.badRequest().body("Usuário já está $status")

        }

        usuario.isAtivo = isAtivo
        repository.save(usuario)

        val status = if (isAtivo) "ativado" else "inativado"
        return ResponseEntity.ok("Usuário $status com sucesso")

    }


    @PatchMapping
    fun atualizarParcial(

        @RequestParam id: Int,
        @RequestParam(required = false) nome: String?,
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) senha: String?,
        @RequestParam(required = false) isAtivo: Boolean?


    ): ResponseEntity<Usuario> {

        val usuarioOptional = repository.findById(id)
        if (usuarioOptional.isEmpty) return ResponseEntity.notFound().build()

        val usuario = usuarioOptional.get()

        nome?.let { usuario.nome = it }
        email?.let { usuario.email = it }
        senha?.let { usuario.senha = it }
        isAtivo?.let { usuario.isAtivo = it }

        val atualizado = repository.save(usuario)
        return ResponseEntity.ok(atualizado)

    }



}