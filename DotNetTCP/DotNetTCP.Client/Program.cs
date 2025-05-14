using System.Diagnostics;
using System.Net.Sockets;

const int MAX_SIZE = 1 << 30;
const string address = "dotnettcp-server";
const int port = 9000;

using var client = new TcpClient();
client.Connect(address, port);
Console.WriteLine($"Conectado ao servidor em {address}:{port}\n");

using NetworkStream stream = client.GetStream();

for (int size = 2; size > 0 && size <= MAX_SIZE; size <<= 1)
{
    byte[] payload = Enumerable.Repeat((byte)'A', size).ToArray();

    var stopwatch = Stopwatch.StartNew();
    stream.Write(payload, 0, payload.Length);

    byte[] response = new byte[size];
    int totalRead = 0;
    while (totalRead < size)
    {
        int bytesRead = stream.Read(response, totalRead, size - totalRead);
        if (bytesRead == 0)
            throw new IOException("Conexão fechada inesperadamente pelo servidor.");
        totalRead += bytesRead;
    }
    stopwatch.Stop();

    long elapsedNs = stopwatch.Elapsed.Ticks * 100;
    decimal seconds = elapsedNs / 1_000_000_000M;
    Console.WriteLine(
        $"Tamanho: {size,8} bytes => Tempo de resposta: {Math.Round(seconds, 6):F6} s");
}
