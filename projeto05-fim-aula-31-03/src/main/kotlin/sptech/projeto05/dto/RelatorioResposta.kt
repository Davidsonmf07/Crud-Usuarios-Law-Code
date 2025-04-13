package sptech.projeto05.dto

/*
Isso é um DTO (Data Transfer Object)
São classes que servem para transferir dados entre camadas da aplicação

Em APIs REST normalmente são usadas para Requisições e Respostas
 */
data class RelatorioResposta(
    val total: Int,
    val proprias: Int,
    val improprias: Int
)
