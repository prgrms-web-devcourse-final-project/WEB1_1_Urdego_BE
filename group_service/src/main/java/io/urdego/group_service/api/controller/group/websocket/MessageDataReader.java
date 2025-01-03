package io.urdego.group_service.api.controller.group.websocket;

import kotlin.TypeCastException;
import org.springframework.stereotype.Component;

@Component
public class MessageDataReader {

    public String readString(Object data) {
        if (data instanceof String) {
            return (String)data;
        }
        throw new TypeCastException();
    }

    public Long readLong(Object data) {
        return Long.parseLong(readString(data));
    }
}
