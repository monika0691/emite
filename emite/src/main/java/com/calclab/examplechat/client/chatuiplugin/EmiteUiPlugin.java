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
package com.calclab.examplechat.client.chatuiplugin;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.Plugin;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.platf.client.services.I18nTranslationServiceMocked;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.client.AbstractXmpp;
import com.calclab.emite.client.Xmpp;
import com.calclab.emite.client.core.bosh.BoshManager;
import com.calclab.emite.client.im.chat.Chat;
import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.examplechat.client.chatuiplugin.dialog.MultiChat;
import com.calclab.examplechat.client.chatuiplugin.dialog.MultiChatListener;
import com.calclab.examplechat.client.chatuiplugin.groupchat.GroupChat;
import com.calclab.examplechat.client.chatuiplugin.pairchat.PairChatPresenter;
import com.calclab.examplechat.client.chatuiplugin.pairchat.PairChatUser;
import com.calclab.examplechat.client.chatuiplugin.params.MultiChatCreationParam;

public class EmiteUiPlugin extends Plugin {

    // Input events
    public static final String OPEN_MULTI_CHAT_DIALOG = "emiteuiplugin.openchatdialog";
    public static final String ACTIVATE_CHAT = "emiteuiplugin.activatechat";
    public static final String CLOSE_CHAT_DIALOG = "emiteuiplugin.closechatdialog";

    // Output messages
    public static final String ON_USER_COLOR_SELECTED = "emiteuiplugin.usercoloselected";
    public static final String ON_PAIR_CHAT_START = "emiteuiplugin.onpairchatstart";
    public static final String ON_STATE_CONNECTED = "emiteuiplugin.onstateconnected";
    public static final String ON_STATE_DISCONNECTED = "emiteuiplugin.onstatedisconnected";
    public static final String ON_PANIC = "emiteuiplugin.onpanic";

    private MultiChat multiChatDialog;

    public EmiteUiPlugin() {
        super("emiteuiplugin");
    }

    @Override
    protected void start() {

        final Dispatcher dispatcher = getDispatcher();
        dispatcher.subscribe(OPEN_MULTI_CHAT_DIALOG, new Action<MultiChatCreationParam>() {
            public void execute(final MultiChatCreationParam param) {
                if (multiChatDialog == null) {
                    createChatDialog(param);
                }
                multiChatDialog.show();
            }

            private void createChatDialog(final MultiChatCreationParam param) {
                final PairChatUser sessionUser = param.getSessionUser();

                final AbstractXmpp xmpp = Xmpp.create(param.getBoshOptions());

                multiChatDialog = ChatDialogFactoryImpl.App.getInstance().createMultiChat(xmpp, sessionUser,
                        param.getUserPassword(), new I18nTranslationServiceMocked(), new MultiChatListener() {

                            public void attachToExtPoint(final UIExtensionElement extensionElement) {
                                dispatcher.fire(PlatformEvents.ATTACH_TO_EXT_POINT, extensionElement);
                            }

                            public void doAction(final String eventId, final Object param) {
                                dispatcher.fire(eventId, param);
                            }

                            public void onCloseGroupChat(final GroupChat groupChat) {
                            }

                            public void onClosePairChat(final PairChatPresenter pairChat) {
                            }

                            public void onUserColorChanged(final String color) {
                                dispatcher.fire(EmiteUiPlugin.ON_USER_COLOR_SELECTED, color);
                            }

                            public void setGroupChatSubject(final Chat groupChat, final String subject) {
                                Log.info("Group '" + groupChat + "' changed subject to '" + subject
                                        + "' (not implemented yet emite connection");
                            }

                            public void setPresenceStatusText(final String statusMessageText) {
                                Log.info("Setting status text" + statusMessageText);
                            }
                        });

                dispatcher.subscribe(EmiteUiPlugin.ON_PAIR_CHAT_START, new Action<XmppURI>() {
                    public void execute(final XmppURI param) {
                        xmpp.getChat().newChat(param);
                    }
                });

                dispatcher.subscribe(CLOSE_CHAT_DIALOG, new Action<Object>() {
                    public void execute(final Object param) {
                        multiChatDialog.closeAllChats(true);
                    }
                });

                dispatcher.subscribe(ACTIVATE_CHAT, new Action<Chat>() {
                    public void execute(final Chat chat) {
                        multiChatDialog.activateChat(chat);
                    }
                });

                dispatcher.subscribe(ON_PANIC, new Action<Object>() {
                    public void execute(final Object obj) {
                        xmpp.getDispatcher().publish(BoshManager.Events.error);
                    }
                });

            }
        });
    }

    @Override
    protected void stop() {
        multiChatDialog.destroy();
    }

}
