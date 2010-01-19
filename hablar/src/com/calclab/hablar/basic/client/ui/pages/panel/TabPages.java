package com.calclab.hablar.basic.client.ui.pages.panel;

import com.calclab.hablar.basic.client.ui.page.HeaderStyles;
import com.calclab.hablar.basic.client.ui.page.Page;
import com.calclab.hablar.basic.client.ui.page.PageHeader;
import com.calclab.hablar.basic.client.ui.pages.PagesPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabPages extends Composite implements PagesPanel {

    interface PagesTabsUiBinder extends UiBinder<Widget, TabPages> {
    }

    interface TabStyles extends HeaderStyles {

    }

    private static PagesTabsUiBinder uiBinder = GWT.create(PagesTabsUiBinder.class);

    @UiField
    TabLayoutPanel tabs;

    @UiField
    TabStyles headerStyle;

    public TabPages() {
	initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void addPage(Page page) {
	PageHeader header = page.getHeader();
	header.setStyles(headerStyle);
	tabs.add((Widget) page, (Widget) header);
    }

    @Override
    public boolean hasPage(Page page) {
	return tabs.getWidgetIndex((Widget) page) != -1;
    }

    @Override
    public void removePage(Page page) {
	tabs.remove((Widget) page);
    }

    @Override
    public void showPage(Page page) {
	int index = tabs.getWidgetIndex((Widget) page);
	tabs.selectTab(index);
    }

}