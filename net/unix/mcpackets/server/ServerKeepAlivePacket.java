package net.unix.mcpackets.server;

import java.io.IOException;

import net.unix.mcpackets.Packet;
import net.unix.mcpackets.PacketInputStream;
import net.unix.mcpackets.PacketOutputStream;

public class ServerKeepAlivePacket extends Packet {
	public int keepAliveID;

	public ServerKeepAlivePacket() {
	}

	public ServerKeepAlivePacket(int keepAliveID) {
		this.keepAliveID = keepAliveID;
	}

	public boolean hasPriority() {
		return true;
	}

	public void read(PacketInputStream pis) throws IOException {
		this.keepAliveID = pis.readVarInt();
	}

	public void write(PacketOutputStream pos) throws IOException {
		pos.writeVarInt(0);
		pos.writeVarInt(this.keepAliveID);
	}
}
