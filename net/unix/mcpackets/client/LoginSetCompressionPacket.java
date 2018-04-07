package net.unix.mcpackets.client;

import java.io.IOException;

import net.unix.mcpackets.Packet;
import net.unix.mcpackets.PacketInputStream;
import net.unix.mcpackets.PacketOutputStream;

public class LoginSetCompressionPacket extends Packet {
	public int threshold;

	public LoginSetCompressionPacket() { }

	public LoginSetCompressionPacket(int threshold) {
		this.threshold = threshold;
	}

	public void read(PacketInputStream pis) throws IOException {
		this.threshold = pis.readVarInt();
	}

	public void write(PacketOutputStream pos) throws IOException {
		pos.writeVarInt(3);
		pos.writeVarInt(this.threshold);
	}
}
