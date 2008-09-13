package com.calclab.emite.im.client.roster;

import static com.calclab.emite.core.client.xmpp.stanzas.XmppURI.uri;
import static com.calclab.emite.testing.MockitoEmiteHelper.isListOfSize;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.calclab.emite.core.client.xmpp.stanzas.IQ;
import com.calclab.emite.core.client.xmpp.stanzas.Presence;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.core.client.xmpp.stanzas.IQ.Type;
import com.calclab.emite.im.client.roster.Roster;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.RosterManager;
import com.calclab.emite.im.client.roster.RosterManagerImpl;
import com.calclab.emite.im.client.roster.RosterManager.SubscriptionMode;
import com.calclab.emite.testing.MockedSession;
import com.calclab.suco.testing.signal.MockSlot;

public class RosterManagerTest {
    private RosterManager manager;
    private Roster roster;
    private MockedSession session;

    @Before
    public void aaCreate() {
	session = new MockedSession();
	roster = mock(Roster.class);
	manager = new RosterManagerImpl(session, roster);

    }

    @Test
    public void shouldAcceptAutomatically() {
	manager.setSubscriptionMode(SubscriptionMode.autoAcceptAll);
	session.receives(new Presence(Presence.Type.subscribe, uri("from@domain"), uri("to@domain")));
	session.verifySent("<presence type='subscribed' />");
	session.verifySent("<presence type='subscribe' to='from@domain' />");
    }

    @Test
    public void shouldAddNewRosterItemWhenSubscriptionAccepted() {
	manager.acceptSubscription(new Presence(Presence.Type.subscribe, uri("from@domain"), uri("to@domain")));
	Mockito.verify(roster).add((RosterItem) anyObject());
    }

    @Test
    public void shouldAddRosterItem() {
	session.setLoggedIn(uri("user@domain/res"));
	manager.requestAddItem(uri("name@domain/res"), "the name", "the group");
	verify(roster).add((RosterItem) anyObject());
	session.verifyIQSent("<iq from='user@domain/res' type='set'><query xmlns='jabber:iq:roster'>"
		+ "<item jid='name@domain/res' name='the name'><group>the group</group></item></query></iq>");
	session.answerSuccess();
    }

    @Test
    public void shouldHandlePresence() {
	session.receives("<presence from='userInRoster@domain/res' to='user@domain/res'>"
		+ "<priority>2</priority></presence>");
	verify(roster).changePresence(eq(uri("userInRoster@domain/res")), (Presence) anyObject());
    }

    @Test
    public void shouldHandlePresenceWithUncompleteJid() {
	session.receives("<presence from='userInRoster' to='user@domain/res'>" + "<priority>2</priority></presence>");
	verify(roster).changePresence(eq(uri("userInRoster")), (Presence) anyObject());
    }

    @Test
    public void shouldRejectAutomatically() {
	manager.setSubscriptionMode(SubscriptionMode.autoRejectAll);
	session.receives(new Presence(Presence.Type.subscribe, uri("from@domain"), uri("to@domain")));
	session.verifySent("<presence type='unsubscribed' />");
    }

    @Test
    public void shouldRemoveItemsToRoster() {
	final XmppURI uri = uri("name@domain/res");
	manager.requestRemoveItem(uri);
	session.verifyIQSent(new IQ(Type.set));
	session.answerSuccess();
	verify(roster).removeItem(uri);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRequestRosterOnLogin() {
	session.setLoggedIn("user@domain/res");
	session.verifyIQSent(new IQ(IQ.Type.get).WithQuery("jabber:iq:roster"));
	session.answer("<iq type='result' xmlns='jabber:client'><query xmlns='jabber:iq:roster'>"
		+ "<item jid='name1@domain' subscription='both' name='complete name1' />"
		+ "<item jid='name2@domain' subscription='both' name='complete name2' />" + "</query></iq>");
	verify(roster).setItems(isListOfSize(2));
    }

    @Test
    public void shouldRequestSubscribe() {
	manager.requestSubscribe(uri("some@domain/res"));
	session.verifySent("<presence to='some@domain' type='subscribe' />");
    }

    @Test
    public void shouldSignalSubscribtionRequests() {
	final MockSlot<Presence> listener = new MockSlot<Presence>();
	manager.onSubscriptionRequested(listener);
	final Presence presence = new Presence(Presence.Type.subscribe, uri("from@domain"), uri("to@domain"));
	session.receives(presence);
	MockSlot.verifyCalled(listener);
    }

    @Test
    public void shouldSignalUnsibscirvedEvents() {
	final MockSlot<XmppURI> listener = new MockSlot<XmppURI>();
	manager.onUnsubscribedReceived(listener);

	final String presence = "<presence from='contact@example.org' to='user@example.com' type='unsubscribed'/>";
	session.receives(presence);
	MockSlot.verifyCalled(listener);
    }


}