use bigdecimal::BigDecimal;
use num_bigint::BigInt;
use std::io::{self, Read, Write};
use std::net::TcpStream;
use std::time::Instant;

const MAX_SIZE: usize = 1 << 30;

fn main() -> io::Result<()> {
    let addr = "rusttcp-server:9000";
    let mut stream = TcpStream::connect(&addr)?;
    println!("Conectado ao servidor em {}\n", addr);

    let mut reader = stream.try_clone()?;

    let mut size = 2;
    while size <= MAX_SIZE {
        let payload = vec![b'A'; size];

        let start = Instant::now();

        stream.write_all(&payload)?;

        let mut resp = vec![0u8; size];
        reader.read_exact(&mut resp)?;

        let elapsed_ns = start.elapsed().as_nanos();

        let nanos = BigInt::from(elapsed_ns as u64);
        let mut bd = BigDecimal::new(nanos, 9);
        bd = bd.with_scale(6);

        println!(
            "Tamanho: {:6} bytes => Tempo de resposta: {} s",
            size,
            bd
        );

        size *= 2;
    }

    Ok(())
}
