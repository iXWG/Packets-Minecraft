package net.unix.mcpackets.server;

import java.io.IOException;

import net.unix.mcpackets.Packet;
import net.unix.mcpackets.PacketInputStream;
import net.unix.mcpackets.PacketOutputStream;

public class StatusPongPacket extends Packet {
	public long time;

	public StatusPongPacket() { }

	public StatusPongPacket(long time) {
		this.time = time;
	}

	public void read(PacketInputStream pis) throws IOException {
		this.time = pis.readLong();
	}

	public void write(PacketOutputStream pos) throws IOException {
		pos.writeVarInt(1);
		pos.writeLong(this.time);
	}
}
