import java.util.*;

public class Elevator {
    private int id;
    private int currentFloor = 0;
    private Direction currentDirection = Direction.IDLE;
    private ElevatorState state = ElevatorState.IDLE;
    private double currentWeight = 0;
    
    // New dependencies
    private WeightSensor weightSensor;
    private AlarmSystem alarm;
    private boolean doorsOpen = true;

    private TreeSet<Integer> upJobs = new TreeSet<>();
    private TreeSet<Integer> downJobs = new TreeSet<>(Collections.reverseOrder());

    // Updated constructor to accept the sensor and alarm
    public Elevator(int id, WeightSensor weightSensor, AlarmSystem alarm) {
        this.id = id;
        this.weightSensor = weightSensor;
        this.alarm = alarm;
    }

    public void setWeight(double weight) {
        this.currentWeight = weight;
        if (weightSensor.isOverloaded(currentWeight)) {
            this.state = ElevatorState.IDLE; // Cannot move while overloaded
            this.doorsOpen = true;
            alarm.activate();
        } else {
            alarm.deactivate();
        }
    }

    public void addJob(int floor) {
        if (state == ElevatorState.UNDER_MAINTENANCE) return;
        
        if (floor > currentFloor) upJobs.add(floor);
        else if (floor < currentFloor) downJobs.add(floor);
    }

    public boolean isOverloaded() {
        return weightSensor.isOverloaded(currentWeight);
    }

    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public ElevatorState getState() { return state; }
    public void setState(ElevatorState state) { this.state = state; }
    public Direction getDirection() { return currentDirection; }
}