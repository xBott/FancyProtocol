# Fancy Protocol

Fancy protocol is a tiny java library that allows you to easily serialize classes into binary format.

## How to use?

1. Define a packet and its structure. Your class should implement interface **me.bottdev.fancyprotocol.Packet**. Packet should have a constructor with all its fields.
```java
import me.bottdev.fancyprotocol.Packet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateUserPacket implements Packet {
    private final String name;
    private final String password;
    private final int age;
    private final boolean isAdmin;
}
```
2. Create **FancyProtocol** instance
```java
FancyProtocol fancyProtocol = new FancyProtocol();
```
3. Register your packet in **FancyProtocol**. Not necessary for encoding, but required to decode the packet.
```java
fancyProtocol.getPacketRegistry().register(CreateUserPacket.class);
```
4. Encoding. May throw an exception if there's an error while encoding and return empty byte list.
```java
CreateUserPacket packet = new CreateUserPacket("John", "password", 20, true);
byte[] bytes = fancyProtocol.encode(packet);
```
5. Decoding.
```java
byte[] bytes = byte[]{...}
Optional<Packet> packet = fancyProtocol.decode(bytes);
```

## Allowed field types

so far library supports these data types:
* byte
* short
* int
* float
* double
* boolean
* String

## Future Plans

1. Support of list and arrays
2. Support of object fields, not only primitives
3. Annotations for field options
4. A special module for packet routing