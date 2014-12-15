package com.doleestudio.tycheapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by dolee@outlook.com on 14. 12. 15.
 */
public class TicketWidget extends RelativeLayout {
    public TicketWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TicketWidget(Context context) {
        super(context);
    }

    public void setTicket(Ticket ticket) {

    }
}
