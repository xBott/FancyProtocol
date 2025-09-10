package me.bottdev.fancyprotocol.fields;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

public interface PacketField {

    void write(Object value, ByteArrayOutputStream stream);

    Optional<Object> read(ByteArrayInputStream stream);

}
