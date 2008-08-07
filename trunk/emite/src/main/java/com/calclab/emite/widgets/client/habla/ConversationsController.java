package com.calclab.emite.widgets.client.habla;

import java.util.HashMap;

import com.calclab.emite.client.im.chat.Chat;
import com.calclab.emite.client.im.chat.ChatManager;
import com.calclab.emite.widgets.client.chat.ChatWidget;
import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot;

public class ConversationsController {
    private final ChatManager manager;
    private final HashMap<String, ChatWidget> chats;
    private final Provider<ChatWidget> chatWidgetFactory;

    public ConversationsController(final ChatManager manager, final Provider<ChatWidget> chatWidgetFactory) {
	this.manager = manager;
	this.chatWidgetFactory = chatWidgetFactory;
	this.chats = new HashMap<String, ChatWidget>();
    }

    public void setWidget(final ConversationsWidget widget) {
	manager.onChatCreated(new Slot<Chat>() {
	    public void onEvent(final Chat chat) {
		ChatWidget chatWidget = chats.get(chat.getID());
		if (chatWidget == null) {
		    chatWidget = chatWidgetFactory.get();
		    chatWidget.getController().setChat(chat);
		    chats.put(chat.getID(), chatWidget);
		    widget.add(chat.getOtherURI().toString(), chatWidget);
		}
	    }
	});
    }

}