package me.bottdev.fancyprotocol.decoders;

import lombok.RequiredArgsConstructor;
import me.bottdev.fancyprotocol.Decoder;
import me.bottdev.fancyprotocol.Packet;
import me.bottdev.fancyprotocol.PacketRegistry;
import me.bottdev.fancyprotocol.fields.PacketField;
import me.bottdev.fancyprotocol.fields.PacketFieldRegistry;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class SimpleDecoder implements Decoder {

    private final PacketFieldRegistry packetFieldRegistry;
    private final PacketRegistry packetRegistry;

    @Override
    public Optional<Packet> decode(byte[] bytes) {

        try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {

            Class<? extends Packet> packetClass = decodeClassNameHeader(stream);
            if (packetClass == null) {
                System.out.println("Failed to decode packet name header");
                return Optional.empty();
            }

            Optional<Packet> packetOptional = createPacket(packetClass);
            if (packetOptional.isEmpty()) {
                System.out.println("Failed to create packet instance");
                return Optional.empty();
            }

            Packet packet = packetOptional.get();
            boolean decodedFields = decodeFields(packet, stream);
            if (!decodedFields) {
                System.out.println("Failed to decode packet fields");
                return null;
            }

            return Optional.of(packet);

        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private Class<? extends Packet> decodeClassNameHeader(ByteArrayInputStream stream) {
        Optional<PacketField> packetFieldOptional = packetFieldRegistry.get("string");
        if (packetFieldOptional.isEmpty()) {
            return null;
        }
        PacketField packetField = packetFieldOptional.get();
        Optional<Object> classNameOptional = packetField.read(stream);
        if (classNameOptional.isEmpty()) {
            return null;
        }
        String className = classNameOptional.get().toString();
        return packetRegistry.get(className);
    }

    private Optional<Packet> createPacket(Class<? extends Packet> packetClass) {
        try {
            Packet packet = packetClass.getDeclaredConstructor().newInstance();
            return Optional.of(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean decodeFields(Packet packet, ByteArrayInputStream stream) {

        AtomicBoolean result = new AtomicBoolean(true);

        Class<?> clazz = packet.getClass();

        for (var field : clazz.getDeclaredFields()) {

            String type = field.getType().getSimpleName().toLowerCase();

            Optional<PacketField> fieldTypeOptional = packetFieldRegistry.get(type);
            if (fieldTypeOptional.isEmpty()) {
                result.set(false);
                break;
            }
            Optional<?> value = fieldTypeOptional.get().read(stream);
            if (value.isEmpty()) {
                result.set(false);
                break;
            }

            field.setAccessible(true);

            try {
                field.set(packet, value.get());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return result.get();
    }



}
