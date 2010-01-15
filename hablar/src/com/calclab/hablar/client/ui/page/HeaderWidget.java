package com.calclab.hablar.client.ui.page;

import com.calclab.hablar.client.ui.page.Page.Visibility;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * The header of the page. The header has text, icon, close button (optional)
 * and active state. Each PageWidget has one PageHeader. The PageWidget creates
 * the header.
 */
class HeaderWidget extends Composite implements PageHeader {

    interface DefaultStyles extends HeaderStyles {
    }

    interface StackHeaderUiBinder extends UiBinder<Widget, HeaderWidget> {
    }

    private static StackHeaderUiBinder uiBinder = GWT.create(StackHeaderUiBinder.class);

    @UiField
    Label title, close, icon;
    @UiField
    FlowPanel header;

    @UiField
    DefaultStyles defaultStyles;

    private final PageWidget page;
    private String iconClass;
    private HeaderStyles currentStyles;

    private Visibility visibility;

    public HeaderWidget(PageWidget page, boolean closeable) {
	this.page = page;
	Widget element = uiBinder.createAndBindUi(this);
	initWidget(element);
	close.setVisible(closeable);
	this.currentStyles = defaultStyles;
	// UIUtils.setTextSelectionEnabled(title.getElement(), false);
    }

    public void requestFocus() {
	if (visibility == Visibility.closed) {
	    header.getElement().addClassName(currentStyles.requestFocus());
	    header.getElement().removeClassName(currentStyles.closed());
	}
    }

    public void setIconClass(String iconClass) {
	if (this.iconClass != null) {
	    icon.getElement().removeClassName(this.iconClass);
	}
	icon.getElement().addClassName(iconClass);
	this.iconClass = iconClass;

    }

    public void setStyles(HeaderStyles styles) {
	this.currentStyles = styles;
	applyStyles();
    }

    public void setVisibility(Visibility visibility) {
	this.visibility = visibility;
	applyStyles();
    }

    private void applyStyles() {
	header.getElement().removeClassName(currentStyles.requestFocus());
	if (visibility == Visibility.open) {
	    header.getElement().removeClassName(currentStyles.closed());
	    header.getElement().addClassName(currentStyles.open());
	} else if (visibility == Visibility.closed) {
	    header.getElement().removeClassName(currentStyles.open());
	    header.getElement().addClassName(currentStyles.closed());
	}
    }

    @UiHandler("title")
    void handleActivateTitle(ClickEvent event) {
	page.fireOpen();
    }

    @UiHandler("close")
    void handleClose(ClickEvent event) {
	page.fireClose();
    }

    @UiHandler("icon")
    void onIconClicked(ClickEvent event) {
	page.fireOpen();
    }

    void setHeaderTitle(String title) {
	this.title.setText(title);
    }

}