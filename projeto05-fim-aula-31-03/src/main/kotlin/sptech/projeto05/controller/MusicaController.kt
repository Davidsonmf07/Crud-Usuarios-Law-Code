package sptech.projeto05.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sptech.projeto05.entity.Usuario

@RestController
@RequestMapping("/usuarios/base")
class UsuarioBaseController {

    // Lista simulada de usuários (memória)
    private val usuarios = mutableListOf(
        Usuario(1, "João Silva", "joao@email.com"),
        Usuario(2, "Maria Oliveira", "maria@email.com"),
        Usuario(3, "Carlos Souza", "carlos@email.com")
    )

    // GET - Lista todos ou filtra por nome/email
    @GetMapping
    fun listar(@RequestParam(required = false) pesquisa: String?): ResponseEntity<List<Usuario>> {
        val listaFiltrada = if (pesquisa.isNullOrBlank()) {
            usuarios
        } else {
            usuarios.filter {
                it.nome?.contains(pesquisa, ignoreCase = true) == true ||
                        it.email?.contains(pesquisa, ignoreCase = true) == true
            }
        }

        return if (listaFiltrada.isEmpty()) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(listaFiltrada)
        }
    }

    // POST - Adiciona um novo usuário
    @PostMapping
    fun adicionar(@RequestBody novoUsuario: Usuario): ResponseEntity<Usuario> {
        val novoId = (usuarios.maxOfOrNull { it.idUsuario ?: 0 } ?: 0) + 1
        novoUsuario.idUsuario = novoId
        usuarios.add(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuario)
    }

    // GET - Retorna um usuário específico por ID
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuario = usuarios.find { it.idUsuario == id }
        return usuario?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    // PUT - Atualiza um usuário existente
    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Int, @RequestBody usuarioAtualizado: Usuario): ResponseEntity<Usuario> {
        val usuarioExistente = usuarios.find { it.idUsuario == id }
        return if (usuarioExistente == null) {
            ResponseEntity.notFound().build()
        } else {
            val index = usuarios.indexOf(usuarioExistente)
            usuarioAtualizado.idUsuario = id
            usuarios[index] = usuarioAtualizado
            ResponseEntity.ok(usuarioAtualizado)
        }
    }

    // DELETE - Remove um usuário pelo ID
    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Int): ResponseEntity<Void> {
        val foiRemovido = usuarios.removeIf { it.idUsuario == id }
        return if (foiRemovido) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
