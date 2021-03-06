package ks3.oc.conn.handlers;

import ks3.oc.Protocol;
import ks3.oc.chat.ChatDisplay;
import ks3.oc.main.MainWindow;

public class MateHandler implements MessageHandler {

    private final MainWindow main;
    private final ChatDisplay chat;

    public MateHandler(MainWindow main, ChatDisplay chat) {
        this.main = main;
        this.chat = chat;
    }

    @Override
    public void handle() {
        main.setMyTurn(false);
        chat.addChatLine("* You win! Check and mate", Protocol.SYSTEM);
    }
}
