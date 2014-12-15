package com.doleestudio.tycheapp;

import junit.framework.TestCase;

import org.json.JSONObject;

public class TicketTest extends TestCase {

    public void testInstanceOf() throws Exception {
        String data =
                "{\"store_id\":1,\"store_name\":\"놀부 부대찌게\",\"wait_number\":6,\"wait_ahead_count\":7,\"reg_time\":\"2014-12-10T08:29:06.000Z\"}";

        JSONObject jsonObj = new JSONObject(data);
        Ticket ticket = Ticket.instanceOf(jsonObj);
        assertNotNull(ticket);
        assertEquals(1, ticket.getStoreId());
        assertEquals("놀부 부대찌게", ticket.getStoreName());
        assertEquals(6, ticket.getNumber());
        assertEquals(7, ticket.getWaitOrder());
    }
}