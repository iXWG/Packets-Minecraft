package net.unix.mcpackets.client;

import java.io.IOException;

import net.unix.mcpackets.Packet;
import net.unix.mcpackets.PacketInputStream;
import net.unix.mcpackets.PacketOutputStream;

public class LoginStartPacket extends Packet {
	public String name;

	public LoginStartPacket() { }

	public LoginStartPacket(String name) {
		this.name = name;
	}

	public void read(PacketInputStream pis) throws IOException {
		this.name = pis.readString(16);
	}

	public void write(PacketOutputStream pos) throws IOException {
		pos.writeVarInt(0);
		pos.writeString(this.name);
	}
}
