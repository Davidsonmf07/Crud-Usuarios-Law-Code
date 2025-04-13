package sptech.projeto05.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sptech.projeto05.entity.Musica

// @RestController
// @RequestMapping("/musicas")
class MusicaController {

    val musicas = mutableListOf(
        Musica(1, "Aquarela", "Toquinho"),
        Musica(2, "O Bêbado e o Equilibrista", "Elis Regina"),
        Musica(3, "O Mundo é um Moinho", "Cartola"),
        Musica(4, "Cálice", "Chico Buarque"),
        Musica(5, "Apesar de Você", "Chico Buarque")
    )

    @GetMapping
    fun getLista(
        @RequestParam(required = false) pesquisa: String?
    ): ResponseEntity<List<Musica>> {
        if (pesquisa == null) {
            if (musicas.isEmpty()) {
                return ResponseEntity.status(204).build()
            }
            return ResponseEntity.status(200).body(musicas)
        }

        val listaFiltrada = musicas.filter {
            it.nome!!.contains(pesquisa, ignoreCase = true)
                    || it.interprete!!.contains(pesquisa, ignoreCase = true)
        }

        if (listaFiltrada.isEmpty()) {
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaFiltrada)
    }

    @PostMapping
    fun post(@RequestBody novaMusica: Musica):ResponseEntity<Musica> {
        val novoId = if (musicas.isEmpty()) 1 else musicas.maxOf { it.id!! } + 1

        novaMusica.id = novoId
        musicas.add(novaMusica)

        return ResponseEntity.status(201).body(novaMusica)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:Int):ResponseEntity<Void> {
        val idValido = musicas.count { it.id == id } > 0

        if (idValido) {
            musicas.removeIf { it.id == id }
            return ResponseEntity.status(204).build()
        }

        return ResponseEntity.status(404).build()
    }

    @GetMapping("/{id}")
    fun getUm(@PathVariable id:Int):ResponseEntity<Musica> {
        val musicaEncontrada = musicas.find { it.id == id }

        if (musicaEncontrada == null) {
            return ResponseEntity.status(404).build()
        }

        return ResponseEntity.status(200).body(musicaEncontrada)
    }

    @PutMapping("/{id}")
    fun put(@PathVariable id:Int, @RequestBody musicaAtualizada: Musica):ResponseEntity<Musica> {
        val musicaEncontrada = musicas.find { it.id == id }

        if (musicaEncontrada == null) {
            return ResponseEntity.status(404).build()
        }

        val posicaoMusicaEncontrada = musicas.indexOf(musicaEncontrada)
        musicaAtualizada.id = id
        musicaAtualizada.dataCadastro = musicaEncontrada.dataCadastro
        musicas[posicaoMusicaEncontrada] = musicaAtualizada

        return ResponseEntity.status(200).body(musicaAtualizada)
    }

    @PatchMapping("/improprio-menores/{id}")
    fun patchImproprioMenores(@PathVariable id:Int): ResponseEntity<Musica> {
        val musicaEncontrada = musicas.find { it.id == id }

        if (musicaEncontrada == null) {
            return ResponseEntity.status(404).build()
        }

        musicaEncontrada.propriaParaCriancas = false

        return ResponseEntity.status(200).body(musicaEncontrada)
    }

    @PatchMapping("/proprio-menores/{id}")
    fun patchProprioMenores(@PathVariable id:Int): ResponseEntity<Musica> {
        val musicaEncontrada = musicas.find { it.id == id }

        if (musicaEncontrada == null) {
            return ResponseEntity.status(404).build()
        }

        musicaEncontrada.propriaParaCriancas = true

        return ResponseEntity.status(200).body(musicaEncontrada)
    }

    @PatchMapping("/musica-ouvida/{id}")
    fun patchMusicaOuvida(@PathVariable id:Int): ResponseEntity<Musica> {
        val musicaEncontrada = musicas.find { it.id == id }

        if (musicaEncontrada == null) {
            return ResponseEntity.status(404).build()
        }
        musicaEncontrada.quantidadeReproducoes++
        return ResponseEntity.status(200).body(musicaEncontrada)
    }

}