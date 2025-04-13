package sptech.projeto05.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDate

/*
Com @Entity, o Spring vai supor que essa classe 'espelha',
'mapeia' uma tabela chamada 'musica'
 */
@Entity // do pacote jakarta.persistence
data class Musica(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id // do pacote jakarta.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,
    @field:NotBlank @field:Size(min = 1, max = 15) var nome: String?,
    @field:Size(min = 2, max = 20) var interprete: String?,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var dataCadastro: LocalDate = LocalDate.now(),
    var propriaParaCriancas: Boolean = false,
    @field:PositiveOrZero var quantidadeReproducoes: Int = 0,
    /*@field:PastOrPresent var dataLancamento: LocalDate? = null,
    @field:Email var emailInterprete: String? = null,
    @field:CPF var cpfInterprete: String? = null*/
) {
// O JPA exige que exista um construtor vazio nas Entidades
    constructor() : this(null, null, null)
/*
Aqui dizemos que sempre que o construtor vazio for invocado,
será criado uma Musica com id, nome e interprete nulos
 */
}
