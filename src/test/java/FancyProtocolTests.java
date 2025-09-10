import me.bottdev.fancyprotocol.FancyProtocol;
import me.bottdev.fancyprotocol.Packet;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class FancyProtocolTests {

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

        FancyProtocol protocol = new FancyProtocol();
        protocol.getPacketRegistry().register(SetLocationPacket.class);

        byte[] bytes = new byte[] {
                0, 0, 0, 17, 83, 101, 116, 76, 111, 99, 97, 116, 105, 111,
                110, 80, 97, 99, 107, 101, 116, 0, 0, 0, 4, 116, 101, 115, 116,
                63, -16, 0, 0, 0, 0, 0, 0, 64, 89, 0, 0, 0, 0, 0, 0,
                64, 94, -64, 0, 0, 0, 0, 0
        };

        Packet packet = protocol.decode(bytes);
        SetLocationPacket setLocationPacket = new SetLocationPacket("test", 1.0, 100.0, 123.0);

        assert packet.equals(setLocationPacket);
    }

}
