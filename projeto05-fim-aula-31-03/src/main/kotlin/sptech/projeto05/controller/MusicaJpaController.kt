package sptech.projeto05.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sptech.projeto05.dto.RelatorioResposta
import sptech.projeto05.entity.Musica
import sptech.projeto05.repository.MusicaRepository

@RestController
@RequestMapping("/musicas")
class MusicaJpaController(val repositorio: MusicaRepository) {
/*
Definimos um construtor que recebe um objeto do tipo MusicaRepository
Assim, o Spring vai INJETAR um objeto desse tipo pronto para uso.
 */

    @GetMapping
    fun get(): ResponseEntity<List<Musica>> {
        // repositorio.findAll() -> faz um "select * from musica"
        val musicas = repositorio.findAll()

        return if (musicas.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(musicas)
        }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id:Int):
            ResponseEntity<Musica> {
/*
findById() retorna um Optional da entidade
"por baixo dos panos" executa um
"select * from musica where id = ?"
 */
        val musica = repositorio.findById(id)

        /*
ResponseEntity.of(<variável Optional>)
- se a variável Optional tiver valor:
  retorna 200 com o valor no corpo
- caso contrário, retorna 404 sem corpo
         */
        return ResponseEntity.of(musica)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:Int):
            ResponseEntity<Void> {
/*
O existsById() verifica se o id indicado existe na tabela
por baixo dos panos, ele faz um...
"select count(*) from musica where id = ?"
 */
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id)
// por dos panos o deleteById faz um "delete from musica where id = ?"
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    @PostMapping
    fun post(@RequestBody @Valid novaMusica: Musica):
            ResponseEntity<Musica> {
/*
save() pode fazer INSERT ou UPDATE
Se o identificador for vazio, fará INSERT
Caso contrário, o JPA verifica se existe na base.
Se não existir, faz INSERT. Caso contrário faz update

Seu retorno é a entidade salva
Com todos os valores pós atualização preenchidos
 */
        val musica = repositorio.save(novaMusica)
        return ResponseEntity.status(201).body(musica)
    }

    /*
Crie o endpoint PUT /musicas/{id}  {JSON na requisição}
Se o id não existir, retorne 404 sem corpo
Caso contrário, atualize a música e retorne 200
com a música atualizada no corpo da resposta.
     */
    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Int,
        @RequestBody musicaAtualizada: Musica
    ):
            ResponseEntity<Musica> {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        musicaAtualizada.id = id
        val musica = repositorio.save(musicaAtualizada)
        return ResponseEntity.status(200).body(musica)
    }

    /*
Refatore o @PatchMapping("/musica-ouvida/{id}")
Para que faça o que tem que fazer com JPA
     */

    @PatchMapping("/musica-ouvida/{id}")
    fun patchMusicaOuvida(@PathVariable id:Int): ResponseEntity<Musica> {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        val musicaEncontrada =
            repositorio.findById(id).get()
        musicaEncontrada.quantidadeReproducoes++
        repositorio.save(musicaEncontrada)
        return ResponseEntity.status(200).body(musicaEncontrada)
    }

    @PatchMapping("/proprio-menores/{id}")
    fun patchProprioMenores(@PathVariable id:Int): ResponseEntity<Musica> {
        val atualizados = repositorio.atualizarPropriaParaCriancas(
            id, true
        )

        if (atualizados == 0) {
            return ResponseEntity.status(404).build()
        }

        val musicaEncontrada = repositorio.findById(id).get()
        return ResponseEntity.status(200).body(musicaEncontrada)
    }

    // pesquisa-nome?nome=Aleluia
    @GetMapping("/pesquisa-nome")
    fun getPorNome(@RequestParam nome:String):
            ResponseEntity<List<Musica>> {
        val resultado = repositorio.findByNomeContains(nome)

        return if (resultado.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(resultado)
        }
    }

    @GetMapping("/para-menores")
    fun getParaMenores(): ResponseEntity<List<Musica>> {
        val resultado = repositorio.findByPropriaParaCriancasTrue()
        return if (resultado.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(resultado)
        }
    }
/*
endpoint: GET /musicas/relatorio
Retorna uma frase (numa linha só):
"Total de músicas na API: X.
P/ menores: Y. Imprópria p/ menores: Z."
Usem Dynamic Finders
 */
    @GetMapping("/relatorio")
    fun relatorio() : ResponseEntity<RelatorioResposta> {
        val proprias = repositorio.countByPropriaParaCriancas(true)

        val improprias = repositorio.countByPropriaParaCriancas(false)

        val total = proprias + improprias

        val resposta = RelatorioResposta(total, proprias, improprias)

        return ResponseEntity.status(200)
            .body(resposta)
    }
}


