package com.calclab.examplechat.client.chatuiplugin.pairchat;

import com.calclab.examplechat.client.chatuiplugin.abstractchat.AbstractChat;

public interface PairChat extends AbstractChat {

    void addMessage(String userJid, String message);

    PairChatUser getOtherUser();

}
