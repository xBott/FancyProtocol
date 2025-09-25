package me.bottdev.fancyprotocol.fields.types;

import me.bottdev.fancyprotocol.fields.PacketField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Optional;

public class ShortPacketField implements PacketField {

    @Override
    public void write(Object value, ByteArrayOutputStream stream) {
        short s = (Short) value;
        byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort(s).array();
        stream.write(bytes, 0, bytes.length);
    }

    @Override
    public Optional<Object> read(ByteArrayInputStream stream) {
        byte[] bytes = new byte[Short.BYTES];
        int read = stream.read(bytes, 0, Short.BYTES);
        if (read < Short.BYTES) {
            return Optional.empty();
        }
        short s = ByteBuffer.wrap(bytes).getShort();
        return Optional.of(s);
    }

}
