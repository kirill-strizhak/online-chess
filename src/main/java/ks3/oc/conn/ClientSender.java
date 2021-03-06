package ks3.oc.conn;

import ks3.oc.board.BoardState;
import ks3.oc.chat.ChatDisplay;
import ks3.oc.conn.handlers.ClientHandshakeHandler;
import ks3.oc.conn.handlers.MessageHandler;
import ks3.oc.main.MainWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSender extends Sender {

    public ClientSender(MainWindow main, BoardState board, ChatDisplay chat, String host, int port) {
        super(main, board, chat, host, port);
    }

    @Override
    protected Socket openConnection(String host, int port) throws IOException {
        return getSocketFactory().createClient(host, port);
    }

    @Override
    protected void sendHandshake(PrintWriter writer) {
        writer.write(Headers.HANDSHAKE);
        writer.println(main.getMyName());
        writer.flush();
    }

    @Override
    protected void startReceiver(BufferedReader reader) {
        Receiver receiver = new Receiver(main, board, chat, reader, this) {
            @Override
            protected MessageHandler createHandshakeHandler(MainWindow main, ChatDisplay chat, BufferedReader reader) {
                return new ClientHandshakeHandler(main, chat, reader);
            }
        };
        new Thread(receiver).start();
    }
}
