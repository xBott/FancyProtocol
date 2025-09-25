import lombok.*;
import me.bottdev.fancyprotocol.FancyProtocol;
import me.bottdev.fancyprotocol.Packet;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestAllFields {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UserInfoPacket implements Packet {

        private String name;
        private boolean online;
        private byte gender;
        private short age;
        private int doneTasks;
        private float progress;
        private double balance;

    }

    @Test
    public void testEncode() {

        UserInfoPacket packet = UserInfoPacket.builder()
                .name("John Doe")
                .online(true)
                .gender((byte) 0)
                .age((short) 25)
                .doneTasks(2000)
                .progress(0.6f)
                .balance(100000.0)
                .build();

        byte[] bytesToCheck = new byte[] {
                0, 0, 0, 14, 85, 115, 101, 114, 73, 110, 102, 111, 80, 97, 99, 107, 101,
                116, 0, 0, 0, 8, 74, 111, 104, 110, 32, 68, 111, 101, 1, 0, 0, 25, 0, 0,
                7, -48, 63, 25, -103, -102, 64, -8, 106, 0, 0, 0, 0, 0
        };

        FancyProtocol fancyProtocol = new FancyProtocol();
        byte[] bytes = fancyProtocol.encode(packet);

        assert Arrays.equals(bytes, bytesToCheck);

    }

    @Test
    public void testDecode() {

        byte[] bytes = new byte[] {
                0, 0, 0, 14, 85, 115, 101, 114, 73, 110, 102, 111, 80, 97, 99, 107, 101,
                116, 0, 0, 0, 8, 74, 111, 104, 110, 32, 68, 111, 101, 1, 0, 0, 25, 0, 0,
                7, -48, 63, 25, -103, -102, 64, -8, 106, 0, 0, 0, 0, 0
        };

        UserInfoPacket packetToCheck = UserInfoPacket.builder()
                .name("John Doe")
                .online(true)
                .gender((byte) 0)
                .age((short) 25)
                .doneTasks(2000)
                .progress(0.6f)
                .balance(100000.0)
                .build();

        FancyProtocol fancyProtocol = new FancyProtocol();
        fancyProtocol.getPacketRegistry().register(UserInfoPacket.class);
        Packet packet = fancyProtocol.decode(bytes);

        assert packet.equals(packetToCheck);

    }

}
