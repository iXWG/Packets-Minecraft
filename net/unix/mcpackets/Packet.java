package net.unix.mcpackets;

import java.io.IOException;

public abstract class Packet {
	public boolean hasPriority() {
		return false;
	}

	public abstract void read(PacketInputStream pis) throws IOException;

	public abstract void write(PacketOutputStream pos) throws IOException;

}