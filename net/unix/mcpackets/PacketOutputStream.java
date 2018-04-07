package net.unix.mcpackets;

import java.io.IOException;
import java.io.OutputStream;

public class PacketOutputStream extends OutputStream {
	private OutputStream stream;
	private byte[] writeBuffer = new byte[8];

	public PacketOutputStream(OutputStream outputStream) {
		this.stream = outputStream;
	}

	public void write(byte[] b) throws IOException {
		this.stream.write(b);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		this.stream.write(b, off, len);
	}

	public void write(int b) throws IOException {
		this.stream.write(b);
	}

	public void writeDouble(double v) throws IOException {
		writeLong(Double.doubleToLongBits(v));
	}

	public void writeFloat(float v) throws IOException {
		writeInt(Float.floatToIntBits(v));
	}

	public final void writeInt(int v) throws IOException {
		write(v >>> 24 & 0xFF);
		write(v >>> 16 & 0xFF);
		write(v >>> 8 & 0xFF);
		write(v >>> 0 & 0xFF);
	}

	public void writeLong(long v) throws IOException {
		this.writeBuffer[0] = ((byte) (int) (v >>> 56));
		this.writeBuffer[1] = ((byte) (int) (v >>> 48));
		this.writeBuffer[2] = ((byte) (int) (v >>> 40));
		this.writeBuffer[3] = ((byte) (int) (v >>> 32));
		this.writeBuffer[4] = ((byte) (int) (v >>> 24));
		this.writeBuffer[5] = ((byte) (int) (v >>> 16));
		this.writeBuffer[6] = ((byte) (int) (v >>> 8));
		this.writeBuffer[7] = ((byte) (int) (v >>> 0));
		write(this.writeBuffer, 0, 8);
	}

	public void flush() throws IOException {
		this.stream.flush();
	}

	public void close() throws IOException {
		this.stream.close();
	}

	public void writeString(String n) throws IOException {
		byte[] bytes = n.getBytes(Main.UTF_8);
		if (bytes.length > 32767) {
			throw new RuntimeException("String too big (was " + bytes.length + " bytes encoded, max 32767)");
		}
		writeVarInt(bytes.length);
		write(bytes);
	}

	public void writeUnsignedShort(int v) throws IOException {
		write(v >>> 8 & 0xFF);
		write(v >>> 0 & 0xFF);
	}

	public void writeShort(short v) throws IOException {
		write(v >>> 8 & 0xFF);
		write(v >>> 0 & 0xFF);
	}

	public void writeVarInt(int n) throws IOException {
		while ((n & 0xFFFFFF80) != 0) {
			write(n & 0x7F | 0x80);
			n >>>= 7;
		}
		write(n);
	}
}
