package ru.yandex.practicum.analyzer.deserializer;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public abstract class BaseAvroDeserializer<T> implements Deserializer<T> {

    private final Class<T> clazz;

    public BaseAvroDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) return null;

        try {
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            SpecificDatumReader<T> reader = new SpecificDatumReader<>(getSchema());
            return reader.read(null, decoder);
        } catch (IOException e) {
            throw new SerializationException("Deserialization error for " + clazz.getSimpleName(), e);
        }
    }

    protected abstract org.apache.avro.Schema getSchema();
}