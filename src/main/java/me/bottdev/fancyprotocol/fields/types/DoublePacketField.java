package me.bottdev.fancyprotocol.fields.types;

import me.bottdev.fancyprotocol.fields.PacketField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Optional;

public class DoublePacketField implements PacketField {

    @Override
    public void write(Object value, ByteArrayOutputStream stream) {
        double d = (Double) value;
        byte[] bytes = ByteBuffer.allocate(Double.BYTES).putDouble(d).array();
        stream.write(bytes, 0, bytes.length);
    }

    @Override
    public Optional<Object> read(ByteArrayInputStream stream) {
        byte[] bytes = new byte[Double.BYTES];
        int read = stream.read(bytes, 0, Double.BYTES);
        if (read < Double.BYTES) {
            return Optional.empty();
        }
        double d = ByteBuffer.wrap(bytes).getDouble();
        return Optional.of(d);
    }

}
