package net.unix.mcpackets.client;

import java.io.IOException;
import java.util.UUID;

import net.unix.mcpackets.Packet;
import net.unix.mcpackets.PacketInputStream;
import net.unix.mcpackets.PacketOutputStream;

public class LoginSuccessPacket extends Packet {
	public UUID uuid;
	public String username;

	public LoginSuccessPacket() {
	}

	public LoginSuccessPacket(String username) {
		this.username = username;
	}

	public LoginSuccessPacket(UUID uuid, String username) {
		this.uuid = uuid;
		this.username = username;
	}

	public boolean hasPriority() {
		return true;
	}

	public void read(PacketInputStream pis) throws IOException {
		String uuid_ = pis.readString(36);
		if (!uuid_.isEmpty()) {
			if (uuid_.length() == 32) {
				uuid_ = uuid_.substring(0, 8) + "-" + uuid_.substring(8, 12) + "-" + uuid_.substring(12, 16) + "-" + uuid_.substring(16, 20) + "-" + uuid_.substring(20);
			}
			this.uuid = UUID.fromString(uuid_);
		}
		this.username = pis.readString(16);
	}

	public void write(PacketOutputStream pos) throws IOException {
		pos.writeVarInt(2);
		pos.writeString(this.uuid == null ? "" : this.uuid.toString());
		pos.writeString(this.username);
	}
}

