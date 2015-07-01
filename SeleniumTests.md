# Introduction #

Furthermore our conventional JUnit tests, we are using Selenium + TestNG to test our funcionality in a real Browser.

# Setting up #

Create a new profile in your Firefox 3.5:
```
$ firefox -ProfileManager --no-remote
```
call it "selenium" (see SeleniumConstants.FIREFOX\_PROFILE\_NAME).

Install the GWT plugin:
http://gwt.google.com/samples/MissingPlugin/MissingPlugin.html

Additional setup if you want, for instance, to have a profile capable of receive test petitions from other machines:

http://code.google.com/intl/es-ES/webtoolkit/doc/latest/DevGuideTestingRemoteTesting.html#Firefox_Profile

Install other plugins in this firefox profile that maybe you want to use, for example some recommended plugins for development: [firebug](http://getfirebug.com/), and the [Selenium IDE plugin](http://seleniumhq.org/download/).

Close and run again firefox with the same problem:
```
$ firefox -P selenium -no-remote
```
and check that the GWT plugin is installed and enabled correctly.

Install the TestNG Plugin for eclipse (optional, but needed to run the Selenium tests from the UI). The TestNG update site:
> http://beust.com/eclipse/

Configure a test user in your server. We suggest to add two user:
  * test1@localhost, display name: test1, password: test1
  * selenium@localhost, display name: selenium, password: selenium

# Running #

First of all, you need the OOPHM (by now only works with Hablar.html) running.

Later, right click in the "com.calclab.hablar.client.selenium" package of the test directory, and in `Run as`, click in `TestNG test`.

More info:
http://testng.org/doc/documentation-main.html#running-testng

# Developing #

We follow the [PageObject](http://code.google.com/p/selenium/wiki/PageObjects) and [PageFactory](http://code.google.com/p/selenium/wiki/PageFactory) patterns with a little complications because we'll also need i18n support in our UI.