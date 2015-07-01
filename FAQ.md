# Emite FAQ #

## Why do I need to install a proxy ##

Emite its a pure xmpp library (all the code is converted to javascript before execute). But you will need to install a proxy in the server side because of the [same origin policy](http://code.google.com/support/bin/answer.py?answer=77865&topic=13006) security measure in the all the browsers (this may change in the near future: http://ejohn.org/blog/cross-site-xmlhttprequest/)

So we need to install a proxy that redirect all the local HttpRequest coming from the client (your browser) to the real Xmpp Server.

## How can I install a proxy ##

A java implementation of this proxy are bundled for testing purposes in the emite library, but in production environments we should delegate this task in the real server (i.e. Apache2)
You can run the proxy simply typing `mvn jetty:run` inside the emite project directory

## How can I configure the included proxy ##

You can change the default settings in the web.xml included in the sources of emite

## What if my XMPP server doesn't support Http Bind? ##

Bundled is also a java servlet that implements HttpBind [JabberHTTPBind](http://zeank.in-berlin.de/jhb/) (thanks Stefan) that you can use (at your own risk)

## How can I integrate the emite xmpp library in my GWT application? ##

Just include the emite.jar in your app and one inherits in your gwt.xml. See the [QuickStart](http://code.google.com/p/emite/wiki/QuickStart) for more details.

## How can I integrate emiteui in my GWT application ? ##

See the EmiteUIEntryPoint class and the [QuickStart](http://code.google.com/p/emite/wiki/QuickStart) for more details.

## And if I'm not using GWT how can I integrate the emiteui in my web site? ##

Integrate emiteui in another web site must be easy buy you must adapt EmiteUIEntryPoint for your necessities. See EmiteUI.gwt.xml and EmiteUI.html also.

## I'm new with Java and/or GWT, how can I ... ? ##

We recommend you to read and play a little bit with GWT for instance with [Getting Started with GWT](http://code.google.com/webtoolkit/gettingstarted.html), also try to run the GWT Samples. After that try to build, install a run our samples.

## I need to install a xmpp server? ##

Well you can use a proxy to resend the bosh petitions, to another server. But you have to respect the [same origin policy](http://code.google.com/support/bin/answer.py?answer=77865&topic=13006). Also see our [Server and Client Compatibility Matrix](http://code.google.com/p/emite/wiki/ServerAndClientsCompatibility).

## What kind of authentication types emite supports? ##

Sorry, initially we only support PLAIN authentication. Be carefully then with your accounts info.

## How can I translate emiteui to other language ##

As we use emite in other applications that must be translated as a whole (both server and client side), we cannot only use GWT i18n stuff. Then to translate emite we suggest you to use [ConstantsWithLookup](http://google-web-toolkit.googlecode.com/svn/javadoc/1.5/com/google/gwt/i18n/client/ConstantsWithLookup.html#getString(java.lang.String)) or [Dictionary](http://google-web-toolkit.googlecode.com/svn/javadoc/1.5/com/google/gwt/i18n/client/Dictionary.html) in your code.

## Do you will support in the future XEP-XXXX or _foo_ feature? ##

Maybe, see our [issue/enhancement list](http://code.google.com/p/emite/issues/list). Emite is very modular and easy to extend, patches are welcome.

## In emiteui/demo there is a bug, what can I do? ##

Please add `?log_level=DEBUG#` to the url and use firebug firefox extension to see the output. Try to fill us an [issue](http://code.google.com/p/emite/issues/list) with some debug info.

## How can I help? ##

See our [issue list](http://code.google.com/p/emite/issues/list), and maybe subscribe to our development group list. Patches are welcome.

## But, emite scales? ##

If you BOSH connector scales, and your xmpp server scales, ((e)) scales.

## I have an error? It doesn't works for me! (with no more info) ##

Yes? A recommended reading: [How To Ask Questions The Smart Way](http://catb.org/~esr/faqs/smart-questions.html).
