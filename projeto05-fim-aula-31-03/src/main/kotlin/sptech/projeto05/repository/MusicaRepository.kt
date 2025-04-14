package sptech.projeto05.repository

import sptech.projeto05.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface UsuarioRepository : JpaRepository<Usuario, Int> {

    fun findByNomeContains(nome: String): List<Usuario>

    @Transactional
    @Modifying
    @Query("update Usuario u set u.nome = ?2 where u.idUsuario = ?1")
    fun atualizarNome(id: Int, nome: String): Int

    @Transactional
    @Modifying
    @Query("update Usuario u set u.senha = ?2 where u.email = ?1")
    fun redefinirSenhaPorEmail(email: String, novaSenha: String): Int
}