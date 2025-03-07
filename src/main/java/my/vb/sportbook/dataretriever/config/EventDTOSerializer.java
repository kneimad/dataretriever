package my.vb.sportbook.dataretriever.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import my.vb.sportbook.dataretriever.dto.EventDTO;

import java.io.IOException;

public class EventDTOSerializer implements StreamSerializer<EventDTO> {

    private final ObjectMapper objectMapper;

    public EventDTOSerializer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void write(ObjectDataOutput out, EventDTO event) throws IOException {
        String json = objectMapper.writeValueAsString(event);
        out.writeUTF(json);
    }

    @Override
    public EventDTO read(ObjectDataInput in) throws IOException {
        String json = in.readUTF();
        return objectMapper.readValue(json, EventDTO.class);
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {
    }
}

