/**
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
package com.calclab.examplechat.client.chatuiplugin.pairchat;

import com.calclab.examplechat.client.chatuiplugin.abstractchat.AbstractChatPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;

public class PairChatPanel extends AbstractChatPanel implements PairChatView {

    public PairChatPanel(final PairChatPresenter presenter) {
        super(presenter);
        this.addListener(new PanelListenerAdapter() {
            public void onActivate(final Panel panel) {
                presenter.onActivated();
            }

            public void onDeactivate(final Panel panel) {
                presenter.onDeactivate();
            }
        });
    }

}
