package com.doleestudio.tycheapp;

import junit.framework.TestCase;

public class NetworkConnectorTest extends TestCase {

    public void testFetchJson() throws Exception {
        NetworkConnector connector = new NetworkConnector();
        String jasonText = connector.fetchJsonText("http://10.0.3.2:3000/tickets.json", "GET", null);
        assertNotNull(jasonText);
    }
}