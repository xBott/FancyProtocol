package me.bottdev.fancyprotocol;

public interface Decoder {

    Packet decode(byte[] bytes);

}
