package Event;

public abstract class CustomEvent extends sx.blah.discord.api.events.Event {

    private String SenderClassName;

    public CustomEvent(Class SenderClass) {
        SenderClassName = SenderClass.getCanonicalName();
    }

    /**
     * Returns the name of the custom event. The name should be very specific to avoid conflict with other plugins.
     * @return the name of the custom event.
     */
    public abstract String getEventName();

    public String getSenderClassName() {
        return SenderClassName;
    }

    @Override
    public boolean equals(Object o) {
        return toString().equals(o.toString());
    }

    @Override
    public String toString() {
        return getEventName();
    }

}
