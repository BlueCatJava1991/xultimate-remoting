package org.danielli.xultimate.common.serialize.support.protostuff;

import java.io.IOException;
import java.io.OutputStream;

import org.danielli.xultimate.common.serialize.support.JavaAbstractDataOutput;
import org.danielli.xultimate.core.serializer.java.BooleanSerializer;
import org.danielli.xultimate.core.serializer.java.ByteSerializer;
import org.danielli.xultimate.core.serializer.java.DoubleSerializer;
import org.danielli.xultimate.core.serializer.java.FloatSerializer;
import org.danielli.xultimate.core.serializer.java.IntegerSerializer;
import org.danielli.xultimate.core.serializer.java.LongSerializer;
import org.danielli.xultimate.core.serializer.java.ShortSerializer;
import org.danielli.xultimate.core.serializer.java.StringSerializer;
import org.danielli.xultimate.core.serializer.protostuff.RpcProtostuffSerializer;

import com.alibaba.dubbo.common.serialize.ObjectOutput;

public class ProtostuffObjectOutput extends JavaAbstractDataOutput implements ObjectOutput {

	private RpcProtostuffSerializer rpcProtostuffSerializer;
	private OutputStream outputStream;
	
	public ProtostuffObjectOutput(OutputStream outputStream,
			BooleanSerializer booleanSerializer, 
			ByteSerializer byteSerializer,
			ShortSerializer shortSerializer,
			IntegerSerializer integerSerializer,
			LongSerializer longSerializerr,
			FloatSerializer floatSerializer,
			DoubleSerializer doubleSerializer,
			StringSerializer stringSerializer,
			RpcProtostuffSerializer rpcProtostuffSerializer) {
		super(outputStream, booleanSerializer, byteSerializer, shortSerializer, integerSerializer, longSerializerr, floatSerializer, doubleSerializer, stringSerializer);
		
		this.outputStream = outputStream;
		this.rpcProtostuffSerializer = rpcProtostuffSerializer;
	}
	
	@Override
	public void flushBuffer() throws IOException {
		outputStream.flush();
	}

	@Override
	public void writeObject(Object obj) throws IOException {
		if( obj == null ) {
			writeByte((byte) 0);
		} else {
			writeByte((byte) 1);
			rpcProtostuffSerializer.serialize(obj, outputStream);
		}
	}

}
