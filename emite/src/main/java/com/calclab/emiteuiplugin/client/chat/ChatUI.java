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
package com.calclab.emiteuiplugin.client.chat;

import org.ourproject.kune.platf.client.View;

public interface ChatUI {

    void addDelimiter(String date);

    void addInfoMessage(String message);

    void addMessage(String userAlias, String message);

    void clearSavedInput();

    void destroy();

    String getChatTitle();

    boolean getCloseConfirmed();

    String getColor(String userAlias);

    String getSavedInput();

    View getView();

    void highLightChatTitle();

    void onCloseCloseConfirmed();

    void onCurrentUserSend(String message);

    void onInputFocus();

    void onInputUnFocus();

    void saveInput(String inputText);

    void setCloseConfirmed(boolean confirmed);

    void setUserColor(String userAlias, String color);

    void unHighLightChatTitle();

}