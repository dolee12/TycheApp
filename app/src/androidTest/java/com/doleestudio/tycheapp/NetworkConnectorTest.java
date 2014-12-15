package com.doleestudio.tycheapp;

import junit.framework.TestCase;

import java.util.ArrayList;

public class NetworkConnectorTest extends TestCase {

    public void testFetchJson() throws Exception {
        NetworkConnector connector = new NetworkConnector();
        ArrayList<Ticket> ticketArray = connector.fetchJson("http://10.0.2.2:3000/lineups/");
        assertNotNull(ticketArray);
        assertTrue(ticketArray.size() > 0);
    }
}