package org.quiltmc.mapping.impl.serialization;

import java.util.Iterator;
import java.util.List;

import org.quiltmc.mapping.api.serialization.ReadableParser;
import org.quiltmc.mapping.api.serialization.WritableParser;

// TODO: need a new datatype here that does the original parsing, and then parses through this
public class TabSeparatedParser implements ReadableParser<String>, WritableParser<String>  {
	@Override
	public boolean readBoolean(String input) {
		return false;
	}

	@Override
	public byte readByte(String input) {
		return 0;
	}

	@Override
	public short readShort(String input) {
		return 0;
	}

	@Override
	public int readInteger(String input) {
		return 0;
	}

	@Override
	public long readLong(String input) {
		return 0;
	}

	@Override
	public float readFloat(String input) {
		return 0;
	}

	@Override
	public double readDouble(String input) {
		return 0;
	}

	@Override
	public String readString(String input) {
		return null;
	}

	@Override
	public boolean hasField(String name, String parent) {
		return false;
	}

	@Override
	public String field(String name, String parent) {
		return null;
	}

	@Override
	public Iterator<String> list(String input) {
		return null;
	}

	@Override
	public boolean isFieldList(String name, String parent) {
		return false;
	}

	@Override
	public String writeBoolean(boolean value) {
		return null;
	}

	@Override
	public String writeByte(byte value) {
		return null;
	}

	@Override
	public String writeShort(short value) {
		return null;
	}

	@Override
	public String writeInteger(int value) {
		return null;
	}

	@Override
	public String writeLong(long value) {
		return null;
	}

	@Override
	public String writeFloat(float value) {
		return null;
	}

	@Override
	public String writeDouble(double value) {
		return null;
	}

	@Override
	public String writeString(String value) {
		return null;
	}

	@Override
	public String object() {
		return null;
	}

	@Override
	public String field(String parent, String name, String field) {
		return null;
	}

	@Override
	public String list(List<String> list) {
		return null;
	}
}
