package me.bottdev.fancyprotocol.encoders;

import lombok.RequiredArgsConstructor;
import me.bottdev.fancyprotocol.Encoder;
import me.bottdev.fancyprotocol.Packet;
import me.bottdev.fancyprotocol.fields.PacketFieldRegistry;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class SimpleEncoder implements Encoder {

    private final PacketFieldRegistry packetFieldRegistry;

    @Override
    public byte[] encode(Packet packet) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {

            boolean isClassNameHeaderEncoded = encodeClassNameHeader(packet, stream);
            if (!isClassNameHeaderEncoded) {
                System.out.println("Failed to encode packet name header");
                return null;
            }

            encodeFields(packet, stream);
            return stream.toByteArray();
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean encodeClassNameHeader(Packet packet, ByteArrayOutputStream stream) {
        AtomicBoolean result = new AtomicBoolean(true);

        String name = packet.getClass().getSimpleName();

        packetFieldRegistry.get("string").ifPresentOrElse(
            fieldType -> fieldType.write(name, stream),
            () -> result.set(false)
        );

        return result.get();
    }

    private void encodeFields(Packet packet, ByteArrayOutputStream stream) {

        for (Field field : packet.getClass().getDeclaredFields()) {

            boolean encoded = encodeField(field, packet, stream);

            if (!encoded) {
                System.out.println("Failed to encode field " + field.getName() + " of packet " + packet.getClass().getName());
                break;
            }

        }
    }

    private boolean encodeField(Field field, Packet packet, ByteArrayOutputStream stream) {

        AtomicBoolean result = new AtomicBoolean(true);

        field.setAccessible(true);

        String type = field.getType().getSimpleName().toLowerCase();

        try {
            Object value = field.get(packet);
            packetFieldRegistry.get(type).ifPresentOrElse(
                    fieldType -> fieldType.write(value, stream),
                    () -> result.set(false)
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result.get();
    }

}
