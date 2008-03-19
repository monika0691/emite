package com.calclab.examplechat.client;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.client.Xmpp;
import com.calclab.emite.client.bosh.BoshOptions;
import com.calclab.emite.client.log.LoggerOutput;
import com.calclab.emite.client.packet.stanza.Message;
import com.calclab.emite.client.x.im.MessageListener;
import com.calclab.emite.client.x.im.roster.RosterItem;
import com.calclab.emite.client.x.im.roster.RosterListener;
import com.calclab.emite.client.x.im.session.SessionListener;
import com.calclab.emite.client.x.im.session.Session.State;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChatExampleEntryPoint implements EntryPoint {
    private Button btnLogin;
    private Button btnLogout;
    private TextBox messageIn;
    private VerticalPanel messageOutput;
    private PasswordTextBox passwordInput;
    private TextBox toIn;
    private TextBox userNameInput;
    private ListBox userSelector;
    private Xmpp xmpp;
    private ScrollPanel messageOutputWrapper;

    public void onModuleLoad() {
        /*
         * Install an UncaughtExceptionHandler which will produce <code>FATAL</code>
         * log messages
         */
        Log.setUncaughtExceptionHandler();

        // At the moment, in runtime:
        Log.setCurrentLogLevel(Log.LOG_LEVEL_DEBUG);

        Log.getDivLogger().moveTo(10, 290);

        /*
         * Use a deferred command so that the UncaughtExceptionHandler catches
         * any exceptions in onModuleLoadCont()
         */
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                onModuleLoadCont();
            }
        });
    }

    public void onModuleLoadCont() {
        createInterface();

        this.xmpp = Xmpp.create(new BoshOptions("http-bind", "localhost"), new LoggerOutput() {
            public void log(final int level, final String message) {
                print(message);
            }
        });

        xmpp.addSessionListener(new SessionListener() {
            public void onStateChanged(final State old, final State current) {
                print("STATE CHANGED: " + current + " - old: " + old);
                switch (current) {
                case connected:
                    btnLogin.setEnabled(false);
                    btnLogout.setEnabled(true);
                case connecting:
                    btnLogin.setEnabled(false);
                case disconnected:
                    btnLogin.setEnabled(true);
                    btnLogout.setEnabled(false);
                }
            }
        });

        xmpp.getRoster().addListener(new RosterListener() {
            public void onRosterChanged(final List<RosterItem> roster) {
                for (final RosterItem item : roster) {
                    userSelector.addItem(item.getName(), item.getJid());
                }
            }
        });

        xmpp.addMessageListener(new MessageListener() {
            public void onReceived(final Message message) {
                String text = "\nIN [" + message.getFrom() + "]\n";
                text += message.getBody();
                addMessageToOutput(text);
            }
        });

    }

    public void print(final String text) {
        Log.debug(text);
    }

    private HorizontalPanel createButtonsPane() {
        final HorizontalPanel buttons = new HorizontalPanel();
        btnLogin = new Button("Login", new ClickListener() {
            public void onClick(final Widget source) {
                xmpp.login(userNameInput.getText(), passwordInput.getText());
                btnLogin.setEnabled(false);
                btnLogout.setEnabled(true);
            }
        });
        buttons.add(btnLogin);
        btnLogout = new Button("Logout", new ClickListener() {
            public void onClick(final Widget arg0) {
                xmpp.logout();
                btnLogout.setEnabled(true);
                btnLogin.setEnabled(true);
            }
        });
        buttons.add(btnLogout);
        return buttons;
    }

    private void createInterface() {
        final VerticalPanel vertical = new VerticalPanel();
        vertical.add(createButtonsPane());
        vertical.add(createLoginPane());
        vertical.add(createMessagePane());
        vertical.add(createOutputPane());

        RootPanel.get().add(vertical);
    }

    private HorizontalPanel createLoginPane() {
        final HorizontalPanel login = new HorizontalPanel();
        userNameInput = new TextBox();
        passwordInput = new PasswordTextBox();
        login.add(new Label("user name:"));
        login.add(userNameInput);
        login.add(new Label("password"));
        login.add(passwordInput);
        return login;
    }

    private VerticalPanel createMessagePane() {
        final VerticalPanel pane = new VerticalPanel();

        final HorizontalPanel controls = new HorizontalPanel();
        pane.add(controls);

        toIn = new TextBox();
        controls.add(toIn);
        messageIn = new TextBox();
        messageIn.addKeyboardListener(new KeyboardListener() {
            public void onKeyDown(final Widget sender, final char keyCode, final int modifiers) {
            }

            public void onKeyPress(final Widget sender, final char keyCode, final int modifiers) {
                if (keyCode == KeyboardListener.KEY_ENTER) {
                    sendMessageIn();
                }
            }

            public void onKeyUp(final Widget sender, final char keyCode, final int modifiers) {
            }
        });
        controls.add(messageIn);
        final Button btnSend = new Button("send", new ClickListener() {
            public void onClick(Widget arg0) {
                sendMessageIn();
            }
        });
        controls.add(btnSend);

        final HorizontalPanel split = new HorizontalPanel();
        userSelector = new ListBox(true);
        userSelector.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                final String jid = userSelector.getValue(userSelector.getSelectedIndex());
                toIn.setText(jid);
            }
        });
        split.add(userSelector);
        final TabPanel chatTabs = new TabPanel();
        split.add(chatTabs);
        messageOutputWrapper = new ScrollPanel();
        messageOutput = new VerticalPanel();
        messageOutputWrapper.add(messageOutput);
        messageOutputWrapper.setWidth("400");
        messageOutputWrapper.setHeight("200");
        HorizontalPanel messageOutputWrapper2 = new HorizontalPanel();
        messageOutputWrapper2.add(messageOutputWrapper);
        messageOutputWrapper2.setBorderWidth(1);
        split.add(messageOutputWrapper2);

        pane.add(split);
        split.setHeight("100%");

        return pane;
    }

    private HorizontalPanel createOutputPane() {
        final HorizontalPanel split = new HorizontalPanel();
        return split;
    }

    private void addMessageToOutput(final String text) {
        messageOutput.add(new Label(text));
        messageOutputWrapper.setScrollPosition(messageOutput.getOffsetHeight());
        // another way (if we are not using ScrollPanels)
        // DOM.setElementPropertyInt(messageOutputWrapper.getElement(),
        // "scrollTop", messageOutput.getOffsetHeight());
    }

    private void sendMessageIn() {
        String msg = messageIn.getText();
        messageIn.setText("");
        addMessageToOutput("sending: " + msg);
        xmpp.send(toIn.getText(), msg);
        messageIn.setFocus(true);
    }

}
