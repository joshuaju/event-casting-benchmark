package events;

import java.time.Instant;
import java.util.UUID;

public sealed abstract class Event permits AEvent, BEvent, CEvent, DEvent, EEvent
{

    private final String id;
    private final String type;
    private final Instant time;

    public Event(String type)
    {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.time = Instant.now();
    }

    public String getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public Instant getTime()
    {
        return time;
    }
}
