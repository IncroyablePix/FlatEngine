package com.c4nn4.network;

import com.c4nn4.pix_engine.manager.network.message.Messageable;

public enum HGMessages implements Messageable {
    ServerAccept,
    ServerDeny,
    ServerPing,
    MessageAll,
    ServerMessage;

    @Override
    public int getType() {
        return this.ordinal();
    }

    public static HGMessages getTypeFromId(int id) {
        if(id > HGMessages.values().length)
            return null;

        for(HGMessages message : HGMessages.values()) {
            if(message.ordinal() == id)
                return message;
        }

        return null;
    }
}
