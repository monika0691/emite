package com.calclab.emite.client.xmpp.stanzas;

/**
 * 
 * http://www.xmpp.org/drafts/attic/draft-saintandre-xmpp-uri-00.html
 * 
 * <code>XMPP-URI      = "xmpp:" jid [ "/" resource]</code>
 * 
 */

public class XmppURI {
    // TODO: no estoy seguro de las excepciones... mejor devolver null?
    public static XmppURI parseURI(final String xmppUri) {
        final String[] splitted1 = xmppUri.split("\\:");
        if (splitted1.length > 2 || splitted1.length == 2 && !splitted1[0].equals("xmpp")) {
            throw new RuntimeException("Wrong XmppUri format" + xmppUri);
        }
        String uriWithoutXmpp = splitted1.length == 1 ? splitted1[0] : splitted1[1];
        final String[] splitted2 = uriWithoutXmpp.split("/");
        if (splitted2.length != 2 || !(splitted2[1].length() > 0)) {
            throw new RuntimeException("Wrong XmppUri format" + xmppUri);
        }
        return new XmppURI(XmppJID.parseJID(splitted2[0]), splitted2[1]);
    }

    private final XmppJID jid;
    private final String resource;

    /**
     * 
     * @param jid
     * @param resource
     *                <code> resource      = *( unreserved / escaped )
                reserved      = ";" / "/" / "?" / ":" / "@" / "&" / "=" / "+" /
                "$" / "," / "[" / "]" </code>
     * 
     */
    public XmppURI(final XmppJID jid, final String resource) {
        this.jid = jid;
        this.resource = resource;
    }

    public XmppJID getJid() {
        return jid;
    }

    public String getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return "xmpp:" + jid + "/" + resource;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (jid == null ? 0 : jid.hashCode());
        result = prime * result + (resource == null ? 0 : resource.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final XmppURI other = (XmppURI) obj;
        if (jid == null) {
            if (other.jid != null) {
                return false;
            }
        } else if (!jid.equals(other.jid)) {
            return false;
        }
        if (resource == null) {
            if (other.resource != null) {
                return false;
            }
        } else if (!resource.equals(other.resource)) {
            return false;
        }
        return true;
    }

}
