package me.bottdev.fancyprotocol;

import java.util.Optional;

public interface Decoder {

    Optional<Packet> decode(byte[] bytes);

}
