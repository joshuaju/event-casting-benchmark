package events;

import java.util.function.Consumer;

public abstract class Emitter
{

    public Consumer<AEvent> onAEvent;
    public Consumer<BEvent> onBEvent;
    public Consumer<CEvent> onCEvent;
    public Consumer<DEvent> onDEvent;
    public Consumer<EEvent> onEEvent;

    public abstract void emit(Event e);

    public Emitter setOnAEvent(Consumer<AEvent> onAEvent)
    {
        this.onAEvent = onAEvent;
        return this;
    }

    public Emitter setOnBEvent(Consumer<BEvent> onBEvent)
    {
        this.onBEvent = onBEvent;
        return this;
    }

    public Emitter setOnCEvent(Consumer<CEvent> onCEvent)
    {
        this.onCEvent = onCEvent;
        return this;
    }

    public Emitter setOnDEvent(Consumer<DEvent> onDEvent)
    {
        this.onDEvent = onDEvent;
        return this;
    }

    public Emitter setOnEEvent(Consumer<EEvent> onEEvent)
    {
        this.onEEvent = onEEvent;
        return this;
    }

}
