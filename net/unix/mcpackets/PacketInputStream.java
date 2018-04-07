package net.unix.mcpackets;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class PacketInputStream extends InputStream {
	private InputStream stream;
	public int limit = -1;
	private Logger log = Logger.getLogger("PacketInputStream");

	@Override
	public int read() throws IOException {
		if (this.limit == 0) {
			log.warning("Przekroczono limit!");
			return -1;
		}
		if (this.limit > 0) {
			this.limit -= 1;
		}
		int str = this.stream.read();
		if (str == -1) {
			throw new EOFException();
		}
		return str;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		if (this.limit == 0) {
			log.warning("Przekroczono limit!");
			return -1;
		}
		if (this.limit > 0) {
			int readed = this.stream.read(b, off, Math.min(len, this.limit));
			this.limit -= readed;
			return readed;
		}
		return this.stream.read(b, off, len);
	}

	public byte readByte() throws IOException {
		return (byte) read();
	}

	public byte[] readBytes(int len) throws IOException {
		byte[] b = new byte[len];
		while (len > 0) {
			int n = read(b, b.length - len, len);
			if (n < 1) {
				throw new EOFException();
			}
			len -= n;
		}
		return b;
	}

	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public int readInt() throws IOException {
		int ch1 = read();
		int ch2 = read();
		int ch3 = read();
		int ch4 = read();
		if ((ch1 | ch2 | ch3 | ch4) < 0) {
			throw new EOFException();
		}
		return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
	}

	public long readLong() throws IOException {
		byte[] readBuffer = readBytes(8);
		return (readBuffer[0] << 56) + ((readBuffer[1] & 0xFF) << 48) + ((readBuffer[2] & 0xFF) << 40)
				+ ((readBuffer[3] & 0xFF) << 32) + ((readBuffer[4] & 0xFF) << 24) + ((readBuffer[5] & 0xFF) << 16)
				+ ((readBuffer[6] & 0xFF) << 8) + ((readBuffer[7] & 0xFF) << 0);
	}

	public String readString(int maxLen) throws IOException {
		int len = readVarInt();
		if (len > maxLen * 4) {
			throw new IOException("The received encoded string buffer length is longer than maximum allowed (" + len
					+ " > " + maxLen * 4 + ")");
		}
		if (len < 0) {
			throw new IOException("The received encoded string buffer length is less than zero! Weird string!");
		}
		String str = new String(readBytes(len), Main.UTF_8);
		if (str.length() > maxLen) {
			throw new IOException(
					"The received string length is longer than maximum allowed (" + len + " > " + maxLen + ")");
		}
		return str;
	}

	public int readUnsignedShort() throws IOException {
		int ch1 = read();
		int ch2 = read();
		if ((ch1 | ch2) < 0) {
			throw new EOFException();
		}
		return (ch1 << 8) + (ch2 << 0);
	}

	public short readShort() throws IOException {
		int ch1 = read();
		int ch2 = read();
		if ((ch1 | ch2) < 0) {
			throw new EOFException();
		}
		return (short) ((ch1 << 8) + (ch2 << 0));
	}

	public int readVarInt() throws IOException {
		int var = 0;
		int moves = 0;
		byte buff;
		do {
			buff = readByte();
			var |= (buff & 0x7F) << moves++ * 7;
			if (moves > 5) {
				throw new RuntimeException("VarInt too big");
			}
		} while ((buff & 0x80) == 128);
		return var;
	}
}
