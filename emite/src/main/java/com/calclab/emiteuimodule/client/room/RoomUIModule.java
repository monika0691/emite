package com.calclab.emiteuimodule.client.room;

import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.calclab.emite.client.xep.muc.RoomManager;
import com.calclab.emite.client.xmpp.session.ISession;
import com.calclab.emite.client.xmpp.session.SessionImpl;
import com.calclab.emiteuimodule.client.status.StatusUI;
import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.modules.Module;
import com.calclab.suco.client.modules.ModuleBuilder;
import com.calclab.suco.client.scopes.SingletonScope;

public class RoomUIModule implements Module {
    public Class<RoomUIModule> getType() {
	return RoomUIModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
	builder.registerProvider(RoomUIManager.class, new Provider<RoomUIManager>() {
	    public RoomUIManager get() {
		final I18nTranslationService i18n = builder.getInstance(I18nTranslationService.class);
		final RoomManager roomManager = builder.getInstance(RoomManager.class);
		final StatusUI statusUI = builder.getInstance(StatusUI.class);
		final ISession sessionImpl = builder.getInstance(SessionImpl.class);
		final RoomUIManager manager = new RoomUIManager(sessionImpl, roomManager, statusUI, i18n);
		final RoomUICommonPanel roomUICommonPanel = new RoomUICommonPanel(manager, statusUI, i18n);
		manager.init(roomUICommonPanel);
		return manager;
	    }
	}, SingletonScope.class);

    }

}
