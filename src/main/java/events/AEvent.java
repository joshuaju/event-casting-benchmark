package events;

public final class AEvent extends Event
{
    public AEvent()
    {
        super("AEvent");
    }

    public String getA(){
        return "A";
    }
}
