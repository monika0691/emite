package com.calclab.hablar.client.chat;

import java.util.HashMap;

import com.calclab.emite.browser.client.PageAssist;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.hablar.client.pages.PagesWidget;
import com.calclab.suco.client.Suco;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class ChatManagerLogic {
    private final HashMap<Chat, ChatWidget> widgets;
    private final PagesWidget pages;

    public ChatManagerLogic(final PagesWidget pages) {
	this.pages = pages;
	this.widgets = new HashMap<Chat, ChatWidget>();

	ChatManager chatManager = Suco.get(ChatManager.class);
	String chatURI = PageAssist.getMeta("hablar.chatWidget");
	if (chatURI != null) {
	    chatManager.open(XmppURI.uri(chatURI));
	}

	chatManager.onChatCreated(new Listener<Chat>() {
	    @Override
	    public void onEvent(Chat chat) {
		getWidget(chat);
	    }
	});

	chatManager.onChatOpened(new Listener<Chat>() {
	    @Override
	    public void onEvent(Chat chat) {
		pages.show(getWidget(chat));
	    }
	});

    }

    private ChatWidget getWidget(Chat chat) {
	ChatWidget widget = widgets.get(chat);
	if (widget == null) {
	    widget = new ChatWidget(chat);
	    final ChatWidget page = widget;
	    widget.onClose(new Listener0() {
		@Override
		public void onEvent() {
		    pages.remove(page);
		    widgets.remove(page);
		}
	    });
	    widgets.put(chat, widget);
	    pages.add(widget, true);
	}
	return widget;
    }

}
