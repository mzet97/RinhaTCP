import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class EchoServer {
    private static final int PORT = 9000;
    private static final int BUFFER_SIZE = 64 * 1024;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.err.println("Servidor TCP rodando na porta " + PORT + "...");
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                while (true) {
                    Socket client = server.accept();
                    executor.submit(() -> handleClient(client));
                }
            }
        }
    }

    private static void handleClient(Socket client) {
        SocketAddress remote = client.getRemoteSocketAddress();
        System.err.println("Conexão recebida de " + remote);
        try (InputStream in = client.getInputStream();
             OutputStream out = client.getOutputStream()) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            System.err.println("Conexão encerrada: " + remote);
        } catch (IOException e) {
            System.err.println("Erro na conexão " + remote + ": " + e.getMessage());
        } finally {
            try { client.close(); } catch (IOException ignored) {}
        }
    }
}
