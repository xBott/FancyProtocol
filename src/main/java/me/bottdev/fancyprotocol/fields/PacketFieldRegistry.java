package me.bottdev.fancyprotocol.fields;

import me.bottdev.fancyprotocol.fields.types.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PacketFieldRegistry {

    public static PacketFieldRegistry primitive() {
        PacketFieldRegistry registry = new PacketFieldRegistry();
        registry.register("byte", new BytePacketField());
        registry.register("short", new ShortPacketField());
        registry.register("int", new IntPacketField());
        registry.register("float", new FloatPacketField());
        registry.register("double", new DoublePacketField());
        registry.register("boolean", new BooleanPacketField());
        registry.register("string", new StringPacketField());
        return registry;
    }

    private final Map<String, PacketField> fieldTypes = new HashMap<>();

    public boolean isRegistered(String type) {
        return fieldTypes.containsKey(type);
    }

    public void register(String type, PacketField field) {
        if (isRegistered(type)) {
            return;
        }
        fieldTypes.put(type.toLowerCase(), field);
    }

    public Optional<PacketField> get(String type) {
        return Optional.ofNullable(fieldTypes.get(type));
    }

}
