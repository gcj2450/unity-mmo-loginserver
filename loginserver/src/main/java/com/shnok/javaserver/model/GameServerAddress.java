package com.shnok.javaserver.model;

import com.shnok.javaserver.util.IPSubnet;
import lombok.Getter;
import lombok.ToString;

import java.net.UnknownHostException;

@Getter
@ToString
class GameServerAddress extends IPSubnet {
    private final String serverAddress;

    /**
     * Instantiates a new game server address.
     * @param subnet the subnet
     * @param address the address
     * @throws UnknownHostException the unknown host exception
     */
    public GameServerAddress(String subnet, String address) throws UnknownHostException {
        super(subnet);
        serverAddress = address;
    }
}
