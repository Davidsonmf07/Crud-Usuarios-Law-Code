# 🪆 API de Gerenciamento de Usuários


---

## 🚀 Endpoints


### 📄 Listar todos os usuários
`GET`  
http://localhost:8080/usuarios


---

### 🔍 Buscar usuário por ID  
`GET`  
http://localhost:8080/usuarios/3


---

### ➕ Criar novo usuário  
`POST`  
http://localhost:8080/usuarios

#### 🔧 Body (JSON)

{
"nome": "Ana Souza",
"email": "ana@email.com",
"senha": "minhaSenha123",
"isAtivo": true,
"administrador": null
}


---



🔁 Atualizar usuário existente
`PUT`
http://localhost:8080/usuarios/3
{
 "nome": "Ana Atualizada",
  "email": "ana.nova@email.com",
  "senha": "novaSenha456",
  "isAtivo": false,
  "administrador": null
}

---


🔑 Recuperar senha
`PUT`
http://localhost:8080/usuarios/recuperar-senha?id=3&novaSenha=1234&repetirSenha=1234

---


✅ Alterar status (Ativar/Desativar usuário)
`PATCH`
http://localhost:8080/usuarios/3/status?isAtivo=true

---


✏️ Atualizar dados via parâmetros
`PUT`
http://localhost:8080/usuarios?id=3&nome=Zeze&e-mail=zeze@email.com&senha=minhaSenha456


---








👨‍💻 Desenvolvedor
Davidson Ferreira Mendes
📧 davidson@email.com

