import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;

public class EchoClient {
    private static final long MAX_SIZE = 1L << 30;

    public static void main(String[] args) throws IOException {
        String host = "javatcp-server";
        int port = 9000;

        System.out.printf("Conectado ao servidor em %s:%d%n%n", host, port);

        try (Socket socket = new Socket(host, port);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            socket.setTcpNoDelay(true);

            for (long size = 2; size <= MAX_SIZE; size <<= 1) {
                int chunk = (int) size;
                byte[] payload = new byte[chunk];
                Arrays.fill(payload, (byte) 'A');

                long startNs = System.nanoTime();
                out.write(payload);
                out.flush();

                byte[] response = new byte[chunk];
                int totalRead = 0;
                while (totalRead < chunk) {
                    int r = in.read(response, totalRead, chunk - totalRead);
                    if (r < 0) {
                        throw new IOException("Conexão fechada inesperadamente pelo servidor.");
                    }
                    totalRead += r;
                }

                long elapsedNs = System.nanoTime() - startNs;

                BigDecimal seconds = new BigDecimal(elapsedNs)
                        .movePointLeft(9)
                        .setScale(6, RoundingMode.HALF_UP);

                System.out.printf(
                        "Tamanho: %8d bytes => Tempo de resposta: %s s%n",
                        chunk, seconds
                );
            }
        }
    }
}
