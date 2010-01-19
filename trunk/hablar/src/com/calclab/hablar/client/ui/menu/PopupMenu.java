package com.calclab.hablar.client.ui.menu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

public class PopupMenu<T> extends PopupPanel implements PopupMenuView<T> {
    private final MenuBar bar;
    private T target;
    private boolean visible;

    public PopupMenu(final String debugId, final MenuAction<T>... actions) {
	super(true);
	super.ensureDebugId(debugId);
	setStyleName("def-PopupPanel");
	bar = new MenuBar(true);
	for (final MenuAction<T> a : actions) {
	    final MenuAction<T> action = a;
	    final MenuItem addedItem = bar.addItem(action.getHTML(), true, new Command() {
		@Override
		public void execute() {
		    PopupMenu.this.hide();
		    action.execute(target);
		}
	    });
	    addedItem.ensureDebugId(action.getId());
	}
	setWidget(bar);
	visible = false;
    }

    @Override
    public void hide() {
	visible = false;
	super.hide();
    }

    @Override
    public boolean isVisible() {
	return visible;
    }

    @Override
    public void setTarget(final T target) {
	this.target = target;
    }

    @Override
    public void show(final int left, final int top) {
	this.visible = true;
	setPopupPosition(left, top);
	show();
    }

    @Override
    public void showRelativeToMenu(final UIObject target) {
	this.visible = true;
	super.showRelativeTo(target);
    }

}
