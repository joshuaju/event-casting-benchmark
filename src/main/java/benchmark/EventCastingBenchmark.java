package benchmark;

import events.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@BenchmarkMode({Mode.AverageTime, Mode.SingleShotTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 3, time = 1)
public class EventCastingBenchmark
{

    @Param({"10000"})
    private int N_EVENTS;


    private List<Event> events;

    @Setup
    public void setup()
    {
        List<Supplier<Event>> eventFactories = List.of(
                () -> new AEvent(),
                () -> new BEvent(),
                () -> new CEvent(),
                () -> new DEvent(),
                () -> new EEvent()
        );

        events = new ArrayList<>(N_EVENTS);
        for (int i = 0; i < N_EVENTS; i++) {
            var eventFactory = eventFactories.get(i % eventFactories.size());
            events.add(eventFactory.get());
        }
    }

    /* ************************************************************************************************************** */
    @Benchmark
    public void ifElseInstanceOf(Blackhole sink)
    {
        for (var event : events) {
            if (event instanceof AEvent) {
                AEvent a = (AEvent) event;
                sink.consume(a.getA());
            } else if (event instanceof BEvent) {
                BEvent b = (BEvent) event;
                sink.consume(b.getB());
            } else if (event instanceof CEvent) {
                CEvent c = (CEvent) event;
                sink.consume(c.getC());
            } else if (event instanceof DEvent) {
                DEvent d = (DEvent) event;
                sink.consume(d.getD());
            } else if (event instanceof EEvent) {
                EEvent e = (EEvent) event;
                sink.consume(e.getE());
            }
        }
    }

    /* ************************************************************************************************************** */
    @Benchmark
    public void ifElseInstanceOfPatternMatching(Blackhole sink)
    {
        for (var event : events) {
            if (event instanceof AEvent a) {
                sink.consume(a.getA());
            } else if (event instanceof BEvent b) {
                sink.consume(b.getB());
            } else if (event instanceof CEvent c) {
                sink.consume(c.getC());
            } else if (event instanceof DEvent d) {
                sink.consume(d.getD());
            } else if (event instanceof EEvent e) {
                sink.consume(e.getE());
            }
        }
    }

    /* ************************************************************************************************************** */
    @Benchmark
    public void ifElseStringCompare(Blackhole sink)
    {
        for (var event : events) {
            if (event.getType().equals("AEvent")) {
                AEvent a = (AEvent) event;
                sink.consume(a.getA());
            } else if (event.getType().equals("BEvent")) {
                BEvent b = (BEvent) event;
                sink.consume(b.getB());
            } else if (event.getType().equals("CEvent")) {
                CEvent c = (CEvent) event;
                sink.consume(c.getC());
            } else if (event.getType().equals("DEvent")) {
                DEvent d = (DEvent) event;
                sink.consume(d.getD());
            } else if (event.getType().equals("EEvent")) {
                EEvent e = (EEvent) event;
                sink.consume(e.getE());
            }
        }
    }

    /* ************************************************************************************************************** */
    @Benchmark
    public void switchCaseStringCompare(Blackhole sink)
    {
        for (var event : events) {
            switch (event.getType()) {
                case "AEvent":
                    AEvent a = (AEvent) event;
                    sink.consume(a.getA());
                    break;
                case "BEvent":
                    BEvent b = (BEvent) event;
                    sink.consume(b.getB());
                    break;
                case "CEvent":
                    CEvent c = (CEvent) event;
                    sink.consume(c.getC());
                    break;
                case "DEvent":
                    DEvent d = (DEvent) event;
                    sink.consume(d.getD());
                    break;
                case "EEvent":
                    EEvent e = (EEvent) event;
                    sink.consume(e.getE());
                    break;
            }
        }
    }

    /* ************************************************************************************************************** */
    @Benchmark
    public void switchCasePatternMatching(Blackhole sink)
    {
        for (var event : events) {
            switch (event) {
                case AEvent a -> sink.consume(a.getA());
                case BEvent b -> sink.consume(b.getB());
                case CEvent c -> sink.consume(c.getC());
                case DEvent d -> sink.consume(d.getD());
                case EEvent e -> sink.consume(e.getE());
            }
        }
    }

}
