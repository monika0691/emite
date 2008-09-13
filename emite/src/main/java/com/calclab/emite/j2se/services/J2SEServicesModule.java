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
package com.calclab.emite.j2se.services;

import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.services.ConnectorCallback;
import com.calclab.emite.core.client.services.ConnectorException;
import com.calclab.emite.core.client.services.ScheduledAction;
import com.calclab.emite.core.client.services.Services;
import com.calclab.suco.client.container.Container;
import com.calclab.suco.client.module.Module;
import com.calclab.suco.client.provider.Provider;

public class J2SEServicesModule implements Services, Module {
    private final HttpConnector connector;

    private final ThreadScheduler scheduler;
    private final TigaseXMLService xmler;

    public J2SEServicesModule() {
	this(new PrintStreamConnectionListener(System.out));
    }

    public J2SEServicesModule(final HttpConnectorListener listener) {
	this.connector = new HttpConnector(listener);
	scheduler = new ThreadScheduler();
	xmler = new TigaseXMLService();
    }

    public long getCurrentTime() {
	return scheduler.getCurrentTime();
    }

    public void onLoad(final Container container) {
	container.registerProvider(Services.class, new Provider<Services>() {
	    public Services get() {
		return J2SEServicesModule.this;
	    }
	});
    }

    public void schedule(final int msecs, final ScheduledAction action) {
	scheduler.schedule(msecs, action);
    }

    public void send(final String httpBase, final String xml, final ConnectorCallback callback)
	    throws ConnectorException {
	connector.send(httpBase, xml, callback);
    }

    public String toString(final IPacket packet) {
	return xmler.toString(packet);
    }

    public IPacket toXML(final String xml) {
	return xmler.toXML(xml);
    }

}