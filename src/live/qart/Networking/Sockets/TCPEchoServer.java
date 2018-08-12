package live.qart.Networking.Sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by QArt on 2018/8/12.
 */
public class TCPEchoServer {
    private static final int BUFSIZE = 32;

    public static void main(String[] args) throws IOException {

        System.out.println("CLASSPATH=" + System.getProperty("java.class.path"));

        if (args.length != 1) // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);

        int recvMsgSize;
        byte[] receiveBuf = new byte[BUFSIZE];

        while (true) {
            Socket clntSock = servSock.accept();

            SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
            System.out.println("Handling client at " + clientAddress);

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            // Receive until client closes connection, indicated by -1 return
            while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                out.write(receiveBuf, 0, recvMsgSize);
            }
            clntSock.close();
        }
    }
}
