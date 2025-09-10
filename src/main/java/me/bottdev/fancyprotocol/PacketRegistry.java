package me.bottdev.fancyprotocol;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {

    private final Map<String, Class<? extends Packet>> registeredPackets = new HashMap<>();

    public boolean isRegistered(Class<? extends Packet> packetClass) {
        String name = packetClass.getSimpleName();
        return registeredPackets.containsKey(name);
    }

    public void register(Class<? extends Packet> packetClass) {
        if (isRegistered(packetClass)) {
            return;
        }
        String name = packetClass.getSimpleName();
        registeredPackets.put(name, packetClass);
    }

    public Class<? extends Packet> get(String name) {
        return registeredPackets.get(name);
    }

}
