package me.bottdev.fancyprotocol.fields.types;

import me.bottdev.fancyprotocol.fields.PacketField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Optional;

public class FloatPacketField implements PacketField {

    @Override
    public void write(Object value, ByteArrayOutputStream stream) {
        float f = (Float) value;
        byte[] bytes = ByteBuffer.allocate(Float.BYTES).putFloat(f).array();
        stream.write(bytes, 0, bytes.length);
    }

    @Override
    public Optional<Object> read(ByteArrayInputStream stream) {
        byte[] bytes = new byte[Float.BYTES];
        int read = stream.read(bytes, 0, Float.BYTES);
        if (read < Float.BYTES) {
            return Optional.empty();
        }
        float f = ByteBuffer.wrap(bytes).getFloat();
        return Optional.of(f);
    }

}
