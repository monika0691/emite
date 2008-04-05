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
package com.calclab.emite.client.core.bosh;

import com.calclab.emite.client.components.Container;
import com.calclab.emite.client.components.ContainerPlugin;
import com.calclab.emite.client.components.Globals;
import com.calclab.emite.client.core.dispatcher.Dispatcher;
import com.calclab.emite.client.core.dispatcher.DispatcherPlugin;
import com.calclab.emite.client.core.services.Connector;
import com.calclab.emite.client.core.services.Scheduler;
import com.calclab.emite.client.core.services.ServicesPlugin;
import com.calclab.emite.client.core.services.XMLService;

public class BoshPlugin {
    private static final String COMPONENT_BOSH = "bosh:manager";
    private static final String COMPONENT_EMITE = "emite";

    public static Emite getEmite(final Container container) {
	return (Emite) container.get(COMPONENT_EMITE);
    }

    public static void install(final Container container, final BoshOptions options) {
	final Dispatcher dispatcher = DispatcherPlugin.getDispatcher(container);
	final Globals globals = ContainerPlugin.getGlobals(container);
	final Connector connector = ServicesPlugin.getConnector(container);
	final XMLService xmler = ServicesPlugin.getXMLService(container);
	final Scheduler scheduler = ServicesPlugin.getScheduler(container);

	final EmiteBosh emite = new EmiteBosh(dispatcher, xmler);
	container.install(COMPONENT_EMITE, emite);

	final BoshManager boshManager = new BoshManager(dispatcher, globals, connector, scheduler, emite, options);

	container.install(COMPONENT_BOSH, boshManager);

    }

}
