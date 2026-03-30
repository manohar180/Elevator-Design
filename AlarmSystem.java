public interface AlarmSystem {
    void activate();
    void deactivate();
}

class BuzzerAlarm implements AlarmSystem {
    @Override
    public void activate() {
        System.out.println("ALARM: Loud Beep! Weight limit exceeded.");
    }

    @Override
    public void deactivate() {
        System.out.println("Weight normal. Alarm off.");
    }
}