#!/bin/bash

if [ -d target/emite-0.2.1/com.calclab.emiteui.EmiteUI ]
then 
	rm -rf target/emite-0.2.1/com.calclab.emiteui.EmiteUI
fi

mvn gwt:compile