package sptech.projeto05.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity

@Table(name = "usuario")

data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var idUsuario: Int? = null,

    @field:NotBlank(message = "O nome é obrigatório")
    @field:Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
    var nome: String = "",

    @field:Email(message = "E-mail inválido")
    @field:NotBlank(message = "O e-mail é obrigatório")
    var email: String = "",

    @field:NotBlank(message = "A senha é obrigatória")
    var senha: String = "",

    var isAtivo: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "fk_adm")
    @JsonBackReference
    var administrador: Usuario? = null

) {
    constructor() : this(null, "", "", "", true, null)
}
