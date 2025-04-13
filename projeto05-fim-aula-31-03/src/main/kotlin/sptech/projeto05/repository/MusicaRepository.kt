package sptech.projeto05.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import sptech.projeto05.entity.Musica

/*

Toda Repository deve ser subclasse de JpaRepository.
Uma Repository abstrai os métodos de acesso a dados.
Ex:
findAll() abstrai um "select * from musica"
findById() abstrai um "select * from musica where id = ?"
deleteById() abstrai um "delete from musica where id = ?"
save() abstrai um "insert into musica values (?, ?, ?)"
       ou um "update musica set nome = ?, interprete = ? where id = ?"
       dependendo da situação

Note que é uma interface, não uma classe.
O Spring vai criar uma implementação dessa classe cujos métodos
irão usar instruções SQL apropriadas para o banco de dados
configurado para o projeto

Dentro de <> temos 2 tipos
1o. é o tipo da Entidade
2o. é o tipo do Id (PK)
 */
interface MusicaRepository : JpaRepository<Musica, Int> {

/*
Aqui implementamos uma alteração adhock
Sempre que tivermos comandos Update ou Delete,
Temos que ter as anotações @Transactional e @Modifying
NÃO é permitido fazer INSERT dessa forma

A instrução de atualização é feita em JPQL
sempre na anotação @Query
 */
    @Transactional // do pacote jakarta.transaction
    @Modifying
    @Query("update Musica m set m.propriaParaCriancas = ?2 where m.id = ?1")
    fun atualizarPropriaParaCriancas(
        id: Int,
        propriaParaCriancas: Boolean
    ): Int
    // o retorno é o número de registros afetados

    /*
Aqui usamos a técnica chamada
Dynamic Finders
     */
    fun findByNomeContains(nome: String): List<Musica>

    fun findByPropriaParaCriancasTrue(): List<Musica>

    fun countByPropriaParaCriancasTrue(): Int

    fun countByPropriaParaCriancasFalse(): Int

    fun countByPropriaParaCriancas(propria: Boolean): Int



}





