package me.bottdev.fancyprotocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bottdev.fancyprotocol.decoders.SimpleDecoder;
import me.bottdev.fancyprotocol.encoders.SimpleEncoder;
import me.bottdev.fancyprotocol.fields.PacketFieldRegistry;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FancyProtocol {

    private PacketRegistry packetRegistry = new PacketRegistry();
    private PacketFieldRegistry packetFieldRegistry = PacketFieldRegistry.primitive();
    private Encoder encoder = new SimpleEncoder(packetFieldRegistry);
    private Decoder decoder = new SimpleDecoder(packetFieldRegistry, packetRegistry);

    public byte[] encode(Packet packet) {
        return encoder.encode(packet);
    }

    public Optional<Packet> decode(byte[] bytes) {
        return decoder.decode(bytes);
    }

}
