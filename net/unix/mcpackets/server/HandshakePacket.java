package net.unix.mcpackets.server;

import java.io.IOException;

import net.unix.mcpackets.Packet;
import net.unix.mcpackets.PacketInputStream;
import net.unix.mcpackets.PacketOutputStream;


public class HandshakePacket extends Packet {
	public int protocol;
	public String ip;
	public int port;
	public int state;

	public HandshakePacket() { }

	public HandshakePacket(int protocol, String ip, int port, int state) {
		this.protocol = protocol;
		this.ip = ip;
		this.port = port;
		this.state = state;
	}

	public boolean hasPriority() {
		return true;
	}


	public void read(PacketInputStream pis) throws IOException {
		this.protocol = pis.readVarInt();
		this.ip = pis.readString(255);
		this.port = pis.readUnsignedShort();
		this.state = pis.readVarInt();
	}

	public void write(PacketOutputStream pos) throws IOException {
		pos.writeVarInt(0);
		pos.writeVarInt(this.protocol);
		pos.writeString(this.ip);
		pos.writeUnsignedShort(this.port);
		pos.writeVarInt(this.state);
	}
}

