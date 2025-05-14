using System.Net.Sockets;
using System.Net;

const int Port = 9000;
const int BufferSize = 64 * 1024;

var listener = new TcpListener(IPAddress.Any, Port);
listener.Start();
Console.Error.WriteLine($"Servidor TCP rodando na porta {Port}...");

while (true)
{
    TcpClient client = listener.AcceptTcpClient();
    Task.Run(() => HandleConnection(client));
}

static void HandleConnection(TcpClient client)
{
    var remoteEndPoint = client.Client.RemoteEndPoint;
    Console.Error.WriteLine($"Conexão recebida de {remoteEndPoint}");

    using NetworkStream stream = client.GetStream();
    byte[] buffer = new byte[BufferSize];

    try
    {
        int bytesRead;
        while ((bytesRead = stream.Read(buffer, 0, buffer.Length)) > 0)
        {
            stream.Write(buffer, 0, bytesRead);
        }

        Console.Error.WriteLine($"Conexão encerrada: {remoteEndPoint}");
    }
    catch (Exception ex)
    {
        Console.Error.WriteLine($"Erro na conexão {remoteEndPoint}: {ex.Message}");
    }
    finally
    {
        client.Close();
    }
}