package com.calclab.hablar.client.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.calclab.hablar.client.ui.debug.Debug;

public class DebugTest {

    @Test
    public void before() {
	assertEquals("prefix-john-example", Debug.getIdFromJid("prefix-", "john@example/resource"));
    }
}
