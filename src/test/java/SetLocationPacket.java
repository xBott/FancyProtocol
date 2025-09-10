import lombok.Getter;
import me.bottdev.fancyprotocol.Packet;

@Getter
public class SetLocationPacket implements Packet {

    private String target;
    private double x;
    private double y;
    private double z;

    public SetLocationPacket() {}

    public SetLocationPacket(String target, double x, double y, double z) {
        this.target = target;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        SetLocationPacket packet = (SetLocationPacket) obj;
        return this.target.equals(packet.target) &&
                this.x == packet.x &&
                this.y == packet.y &&
                this.z == packet.z;
    }
}
