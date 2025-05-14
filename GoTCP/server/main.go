package main

import (
    "io"
    "log"
    "net"
)

const (
    address    = "0.0.0.0:9000"
    bufferSize = 64 * 1024
)

func handleConnection(conn net.Conn) {
    defer conn.Close()
    remoteAddr := conn.RemoteAddr().String()
    log.Printf("Conexão recebida de %s", remoteAddr)

    buf := make([]byte, bufferSize)
    for {
        n, err := conn.Read(buf)
        if err != nil {
            if err == io.EOF {
                log.Printf("Conexão encerrada: %s", remoteAddr)
            } else {
                log.Printf("Erro ao ler de %s: %v", remoteAddr, err)
            }
            return
        }

        if _, err := conn.Write(buf[:n]); err != nil {
            log.Printf("Erro ao escrever para %s: %v", remoteAddr, err)
            return
        }
    }
}

func main() {
    listener, err := net.Listen("tcp", address)
    if err != nil {
        log.Fatalf("Falha ao iniciar servidor: %v", err)
    }
    defer listener.Close()

    log.Printf("Servidor TCP rodando na porta %s...", address)

    for {
        conn, err := listener.Accept()
        if err != nil {
            log.Printf("Falha ao aceitar conexão: %v", err)
            continue
        }
        go handleConnection(conn)
    }
}
