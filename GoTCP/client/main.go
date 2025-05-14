package main

import (
    "bytes"
    "fmt"
    "io"
    "log"
    "math/big"
    "net"
    "time"
)

func main() {
    addr := "gotcp-server:9000"
    conn, err := net.Dial("tcp", addr)
    if err != nil {
        log.Fatalf("Erro ao conectar: %v\n", err)
    }
    defer conn.Close()
    fmt.Printf("Conectado ao servidor em %s\n\n", addr)

    const maxSize = 1 << 30
    for size := 2; size <= maxSize; size *= 2 {
        payload := bytes.Repeat([]byte{'A'}, size)

        start := time.Now()

        if _, err := conn.Write(payload); err != nil {
            log.Fatalf("Erro ao enviar payload: %v\n", err)
        }

        resp := make([]byte, size)
        if _, err := io.ReadFull(conn, resp); err != nil {
            log.Fatalf("Erro ao ler resposta: %v\n", err)
        }

        elapsedNs := time.Since(start).Nanoseconds()

        rat := new(big.Rat).SetFrac(
            big.NewInt(elapsedNs),
            big.NewInt(1_000_000_000),
        )
        diffStr := rat.FloatString(6)
        
        fmt.Printf("Tamanho: %10d bytes → Tempo de resposta: %s s\n", size, diffStr)
    }
}
