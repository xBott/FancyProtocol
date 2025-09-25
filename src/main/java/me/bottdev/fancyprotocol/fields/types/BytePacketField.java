package me.bottdev.fancyprotocol.fields.types;

import me.bottdev.fancyprotocol.fields.PacketField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

public class BytePacketField implements PacketField {

    @Override
    public void write(Object value, ByteArrayOutputStream stream) {
        byte b = (Byte) value;
        stream.write(b);
    }

    @Override
    public Optional<Object> read(ByteArrayInputStream stream) {
        byte[] bytes = new byte[1];
        int read = stream.read(bytes, 0, 1);
        if (read < 1) {
            return Optional.empty();
        }
        byte b = bytes[0];
        return Optional.of(b);
    }

}
