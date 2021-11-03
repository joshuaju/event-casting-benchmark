package benchmark;

import events.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

@BenchmarkMode({Mode.AverageTime, Mode.SingleShotTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 5)
@Warmup(iterations = 10, time = 150, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 150, timeUnit = TimeUnit.MILLISECONDS)
public class EventCastingBenchmark
{

    @Param({"10000"})
    private int N_EVENTS;

    @Param({"off", "shuffle", "random"})
    private String SHUFFLE;

    private List<Event> events;

    @Setup()
    public void setup()
    {
        List<Supplier<Event>> eventFactories = List.of(
                () -> new AEvent(),
                () -> new BEvent(),
                () -> new CEvent(),
                () -> new DEvent(),
                () -> new EEvent()
        );

        switch (SHUFFLE) {
            case "off" -> events = abcde(eventFactories);
            case "shuffle" -> events = shuffled(eventFactories);
            case "random" -> events = randomized(eventFactories);
        }
    }

    List<Event> abcde(List<Supplier<Event>> eventFactories)
    {
        return generate(N_EVENTS, eventFactories, nthEvent -> nthEvent % eventFactories.size());
    }

    List<Event> shuffled(List<Supplier<Event>> eventFactories)
    {
        List<Event> events = abcde(eventFactories);
        Collections.shuffle(events);
        return events;
    }

    List<Event> randomized(List<Supplier<Event>> eventFactories)
    {
        Random rnd = new Random();
        return generate(N_EVENTS, eventFactories, nthEvent -> rnd.nextInt(eventFactories.size()));

    }

    List<Event> generate(int N, List<Supplier<Event>> eventFactories, Function<Integer, Integer> getFactoryIndex)
    {
        List<Event> events = new ArrayList<>(N);
        for (int n = 0; n < N; n++) {
            var factoryIndex = getFactoryIndex.apply(n);
            var factory = eventFactories.get(factoryIndex);
            events.add(factory.get());
        }
        return events;
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
