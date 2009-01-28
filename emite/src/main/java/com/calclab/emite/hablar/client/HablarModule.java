package com.calclab.emite.hablar.client;

import com.calclab.emite.browser.client.AutoConfig;
import com.calclab.emite.core.client.xmpp.session.Session;
import com.calclab.emite.hablar.client.pages.roster.RosterView;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.suco.client.Suco;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class HablarModule extends AbstractModule implements EntryPoint {

    public void onModuleLoad() {
	Suco.install(this);
	final PagesContainer widget = Suco.get(PagesContainer.class);
	RootPanel.get("hablar").add(widget);
	Suco.get(AutoConfig.class).run();
    }

    @Override
    protected void onInstall() {
	register(Singleton.class, new Factory<PagesContainer>(PagesContainer.class) {
	    @Override
	    public PagesContainer create() {
		return new PagesContainer($(RosterView.class));
	    }

	    @Override
	    public void onAfterCreated(final PagesContainer instance) {
		new PagesController($(Session.class), $(ChatManager.class), instance);
	    }
	});

    }
}
