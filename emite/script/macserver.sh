#!/bin/bash

rm -rf src/main/webapp/gwt/com.calclab.emiteui.EmiteUI/



if [ ! -d src/main/webapp/gwt ]
then
  mkdir src/main/webapp/gwt
fi

cp -R target/emite-0.2.1/com.calclab.emiteui.EmiteUI/ src/main/webapp/gwt/com.calclab.emiteui.EmiteUI/
cp -R target/emite-0.2.1/com.calclab.emite.examples.chat.Chat/ src/main/webapp/gwt/com.calclab.emite.examples.chat.Chat/

mvn jetty:run