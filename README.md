# Rinha de TCP

Este repositório contém implementações de clientes e servidores TCP em diferentes linguagens: Go, Rust, Java e .NET. Cada subprojeto é independente e pode ser executado com Docker, facilitando testes de interoperabilidade e comparações de desempenho.

## Sumário dos Subprojetos

- **GoTCP/client:** Cliente TCP em Go
- **GoTCP/server:** Servidor TCP em Go
- **RustTCP/tcp-client:** Cliente TCP em Rust
- **RustTCP/tcp-server:** Servidor TCP em Rust
- **JavaTCP/Client:** Cliente TCP em Java
- **JavaTCP/Server:** Servidor TCP em Java
- **DotNetTCP.Client:** Cliente TCP em .NET
- **DotNetTCP.Server:** Servidor TCP em .NET

---

## Como rodar cada projeto (via Docker)

### Passo inicial: criar uma rede Docker customizada
Antes de rodar os containers, crie uma rede para que todos possam se comunicar:
```sh
docker network create rinha-tcp-net
```
Se a rede já existir, este comando não causará erro.

> **Atenção:**
> Antes de rodar qualquer cliente, certifique-se de que o servidor correspondente (por exemplo, rusttcp-server, gotcp-server, javatcp-server ou dotnettcp-server) está rodando e acessível na rede `rinha-tcp-net`. Caso contrário, o cliente apresentará erro de "Connection refused".
>
> Exemplo de execução conjunta:
> ```sh
> docker run --rm --network rinha-tcp-net --name rusttcp-server -p 9000:9000 rusttcp-server
> # Em outro terminal:
> docker run --rm --network rinha-tcp-net --name rusttcp-client rusttcp-client
> ```

### GoTCP/client
```sh
docker build -t gotcp-client ./GoTCP/client
docker run --rm --network rinha-tcp-net --name gotcp-client gotcp-client
```

### GoTCP/server
```sh
docker build -t gotcp-server ./GoTCP/server
docker run --rm --network rinha-tcp-net --name gotcp-server -p 9000:9000 gotcp-server
```

### RustTCP/tcp-client
```sh
docker build -t rusttcp-client ./RustTCP/tcp-client
docker run --rm --network rinha-tcp-net --name rusttcp-client rusttcp-client
```

### RustTCP/tcp-server
```sh
docker build -t rusttcp-server ./RustTCP/tcp-server
docker run --rm --network rinha-tcp-net --name rusttcp-server -p 9000:9000 rusttcp-server
```

### JavaTCP/Client
```sh
docker build -t javatcp-client ./JavaTCP/Client
docker run --rm --network rinha-tcp-net --name javatcp-client javatcp-client
```

### JavaTCP/Server
```sh
docker build -t javatcp-server ./JavaTCP/Server
docker run --rm --network rinha-tcp-net --name javatcp-server -p 9000:9000 javatcp-server
```

### DotNetTCP.Client
```sh
docker build -t dotnettcp-client ./DotNetTCP/DotNetTCP.Client
docker run --rm --network rinha-tcp-net --name dotnettcp-client dotnettcp-client
```

### DotNetTCP.Server
```sh
docker build -t dotnettcp-server ./DotNetTCP/DotNetTCP.Server
docker run --rm --network rinha-tcp-net --name dotnettcp-server -p 9000:9000 dotnettcp-server
```

---

**Obs.:**
- Altere as portas nos comandos `docker run` caso necessário para evitar conflitos.
- Caso a porta 9000 já esteja em uso na sua máquina, você pode trocar o mapeamento de porta do comando, por exemplo: `-p 9001:9000` (isso faz com que a porta 9001 do host seja redirecionada para a 9000 do container).
- Consulte os arquivos `Dockerfile` de cada subprojeto para customizações avançadas ou variáveis de ambiente.
- Para builds nativos, consulte cada subdiretório para instruções específicas sem uso de Docker.

---

## Licença
Este projeto está sob a licença MIT.