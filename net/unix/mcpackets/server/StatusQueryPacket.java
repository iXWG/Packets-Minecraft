package net.unix.mcpackets.server;

import java.io.IOException;

import net.unix.mcpackets.Packet;
import net.unix.mcpackets.PacketInputStream;
import net.unix.mcpackets.PacketOutputStream;

public class StatusQueryPacket extends Packet {

	public void read(PacketInputStream pis) throws IOException {
	}

	public void write(PacketOutputStream pos) throws IOException {
		pos.writeVarInt(0);
	}

	public boolean hasPriority() {
		return true;
	}
}
