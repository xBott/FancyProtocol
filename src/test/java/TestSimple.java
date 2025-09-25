import lombok.*;
import me.bottdev.fancyprotocol.Executable;
import me.bottdev.fancyprotocol.FancyProtocol;
import me.bottdev.fancyprotocol.Packet;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

public class TestSimple {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class SetLocationPacket implements Packet, Executable {

        private String target;
        private double x;
        private double y;
        private double z;

        @Override
        public void execute() {
            System.out.printf("Setting location of %s to x: %s y: %s z: %s%n", target, x, y,z);
        }

    }

    @Test
    public void testEncode() {

        FancyProtocol protocol = new FancyProtocol();
        byte[] bytes = protocol.encode(new SetLocationPacket("test", 1.0, 100.0, 123.0));

        byte[] bytesToCheck = new byte[] {
                0, 0, 0, 17, 83, 101, 116, 76, 111, 99, 97, 116, 105, 111,
                110, 80, 97, 99, 107, 101, 116, 0, 0, 0, 4, 116, 101, 115, 116,
                63, -16, 0, 0, 0, 0, 0, 0, 64, 89, 0, 0, 0, 0, 0, 0,
                64, 94, -64, 0, 0, 0, 0, 0
        };

        assert Arrays.equals(bytes, bytesToCheck);
    }

    @Test
    public void testDecode() {

        byte[] bytes = new byte[] {
                0, 0, 0, 17, 83, 101, 116, 76, 111, 99, 97, 116, 105, 111,
                110, 80, 97, 99, 107, 101, 116, 0, 0, 0, 4, 116, 101, 115, 116,
                63, -16, 0, 0, 0, 0, 0, 0, 64, 89, 0, 0, 0, 0, 0, 0,
                64, 94, -64, 0, 0, 0, 0, 0
        };

        SetLocationPacket packetToCheck = new SetLocationPacket("test", 1.0, 100.0, 123.0);

        FancyProtocol protocol = new FancyProtocol();
        protocol.getPacketRegistry().register(SetLocationPacket.class);
        Optional<Packet> packetOptional = protocol.decode(bytes);

        packetOptional.ifPresent(packet -> {
            if (packet instanceof Executable executable) {
                executable.execute();
            }
        });

        assert packetOptional.isPresent() && packetOptional.get().equals(packetToCheck);
    }

}
