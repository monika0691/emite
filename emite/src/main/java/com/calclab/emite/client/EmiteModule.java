/*
 *
 * ((e)) emite: A pure gwt (Google Web Toolkit) xmpp (jabber) library
 *
 * (c) 2008 The emite development team (see CREDITS for details)
 * This file is part of emite.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.calclab.emite.client;

import com.calclab.emite.client.core.CoreModule;
import com.calclab.emite.client.core.services.gwt.GWTServicesModule;
import com.calclab.emite.client.extra.avatar.AvatarModule;
import com.calclab.emite.client.extra.chatstate.ChatStateModule;
import com.calclab.emite.client.extra.muc.MUCModule;
import com.calclab.emite.client.im.InstantMessagingModule;
import com.calclab.emite.client.modular.Container;
import com.calclab.emite.client.modular.Module;
import com.calclab.emite.client.modular.ModuleContainer;
import com.calclab.emite.client.xmpp.XMPPModule;

public class EmiteModule implements Module {

    public static Xmpp getXmpp(final Container container) {
        return container.getInstance(Xmpp.class);
    }

    public static void load(final ModuleContainer container) {
        container.add(new GWTServicesModule());
        container.add(new CoreModule(), new XMPPModule(), new InstantMessagingModule());
        // FIXME: esto debería ir fuera de aquí
        container.add(new MUCModule(), new AvatarModule(), new ChatStateModule());
        container.add(new EmiteModule());
    }

    public Class<? extends Module> getType() {
        return EmiteModule.class;
    }

    public void onLoad(final Container container) {
        container.registerSingletonInstance(Xmpp.class, new Xmpp(container));
    }

}