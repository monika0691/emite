package com.calclab.emite.widgets.client.base;

import com.calclab.emite.core.client.xmpp.session.Session;
import com.calclab.emite.core.client.xmpp.session.Session.State;
import com.calclab.suco.client.listener.Listener;

public class ComposedController {

    private final Session session;

    public ComposedController(final Session session) {
	this.session = session;
    }

    public void setWidget(final ComposedWidget widget) {
	session.onStateChanged(new Listener<Session.State>() {
	    public void onEvent(final Session.State state) {
		if (state == State.ready) {
		    widget.showConnectedWidget();
		} else {
		    widget.showDisconnectedWiget();
		}
	    }
	});
    }

}
