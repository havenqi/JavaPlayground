package live.qart.Networking.Sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by QArt on 2018/8/12.
 */
public class TCPEchoClient {
    public static void main(String[] args) throws IOException {
        if((args.length < 2) || (args.length > 3))
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

        String server = args[0];

        byte[] data = args[1].getBytes();

        int servPort = (args.length == 3) ? Integer.parseInt(args[2]): 7;

        // Create socket that is connected to server on specific port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server... sending echo string. \n" +
                "sendBuffSize= " + socket.getSendBufferSize() + "\n" +
                "recvBuffSize= " + socket.getReceiveBufferSize());

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(data); // send encoded string to server

        // Receive the same string back from the server
        int totalBytesRcvd = 0;
        int bytesRcvd;  // Bytes received in last read
        while(totalBytesRcvd < data.length) {
            if((bytesRcvd = in.read(data, totalBytesRcvd,
                    data.length - totalBytesRcvd)) == -1)
                throw new SocketException("Connection closed prematurely");
            totalBytesRcvd += bytesRcvd;
            System.out.println("Read one time->");
        } // data array is full

        System.out.println("Received: " + new String(data));

        socket.close();   // close socket and it's streams

    }
}
