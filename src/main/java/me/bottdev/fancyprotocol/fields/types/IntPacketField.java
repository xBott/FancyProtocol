package me.bottdev.fancyprotocol.fields.types;

import me.bottdev.fancyprotocol.fields.PacketField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Optional;

public class IntPacketField implements PacketField {

    @Override
    public void write(Object value, ByteArrayOutputStream stream) {
        int integer = (Integer) value;
        byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(integer).array();
        stream.write(bytes, 0, bytes.length);
    }

    @Override
    public Optional<Object> read(ByteArrayInputStream stream) {
        byte[] bytes = new byte[Integer.BYTES];
        int read = stream.read(bytes, 0, Integer.BYTES);
        if (read < Integer.BYTES) {
            return Optional.empty();
        }
        int integer = ByteBuffer.wrap(bytes).getInt();
        return Optional.of(integer);
    }

}
