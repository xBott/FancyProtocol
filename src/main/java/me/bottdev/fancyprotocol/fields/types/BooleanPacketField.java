package me.bottdev.fancyprotocol.fields.types;

import me.bottdev.fancyprotocol.fields.PacketField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

public class BooleanPacketField implements PacketField {

    @Override
    public void write(Object value, ByteArrayOutputStream stream) {
        Boolean bool = (Boolean) value;
        stream.write(bool ? 1 : 0);
    }

    @Override
    public Optional<Object> read(ByteArrayInputStream stream) {
        byte[] bytes = new byte[1];
        int read = stream.read(bytes, 0, 1);
        if (read < 1) {
            return Optional.empty();
        }
        boolean integer = bytes[0] == 1;
        return Optional.of(integer);
    }

}
