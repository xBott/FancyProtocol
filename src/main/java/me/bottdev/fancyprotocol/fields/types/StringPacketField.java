package me.bottdev.fancyprotocol.fields.types;

import me.bottdev.fancyprotocol.fields.PacketField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class StringPacketField implements PacketField {

    @Override
    public void write(Object value, ByteArrayOutputStream stream) {
        String str = (String) value;
        byte[] data = str.getBytes(StandardCharsets.UTF_8);

        byte[] lengthBytes = ByteBuffer.allocate(Integer.BYTES).putInt(data.length).array();
        stream.write(lengthBytes, 0, lengthBytes.length);

        stream.write(data, 0, data.length);
    }

    @Override
    public Optional<Object> read(ByteArrayInputStream stream) {
        try {

            byte[] lengthBytes = new byte[Integer.BYTES];
            if (stream.read(lengthBytes) < Integer.BYTES) {
                return Optional.empty();
            }
            int length = ByteBuffer.wrap(lengthBytes).getInt();

            byte[] strBytes = new byte[length];
            if (stream.read(strBytes) < length) {
                return Optional.empty();
            }

            return Optional.of(new String(strBytes, StandardCharsets.UTF_8));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
