import java.util.*;

public class ElevatorController {
    private List<Elevator> elevators;
    private SelectionStrategy selectionStrategy;
    private Set<Integer> maintenanceFloors = new HashSet<>();

    public ElevatorController(int numElevators, SelectionStrategy strategy) {
        this.elevators = new ArrayList<>();
        this.selectionStrategy = strategy;
        
        for (int i = 0; i < numElevators; i++) {
            // Fix: Injecting the required dependencies into the Elevator constructor
            WeightSensor sensor = new WeightSensor(750.0); 
            AlarmSystem alarm = new BuzzerAlarm(); 
            elevators.add(new Elevator(i, sensor, alarm));
        }
    }

    public void setMaintenanceFloor(int floor, boolean isUnderMaintenance) {
        if (isUnderMaintenance) {
            maintenanceFloors.add(floor);
        } else {
            maintenanceFloors.remove(floor);
        }
    }

    public void setElevatorMaintenance(int elevatorId, ElevatorState state) {
        if (elevatorId >= 0 && elevatorId < elevators.size()) {
            elevators.get(elevatorId).setState(state);
        }
    }

    public void handleExternalRequest(ExternalRequest request) {
        if (maintenanceFloors.contains(request.getSourceFloor())) {
            System.out.println("Floor " + request.getSourceFloor() + " is under maintenance. Buttons disabled.");
            return;
        }
        
        Elevator selected = selectionStrategy.selectElevator(elevators, request);
        if (selected != null) {
            selected.addJob(request.getSourceFloor());
        }
    }

    public void handleInternalRequest(int elevatorId, InternalRequest request) {
        Elevator e = elevators.get(elevatorId);
        
        // Check both maintenance and weight before accepting internal job
        if (e.getState() != ElevatorState.UNDER_MAINTENANCE && !e.isOverloaded()) {
            e.addJob(request.getDestinationFloor());
        } else if (e.isOverloaded()) {
            System.out.println("Elevator " + elevatorId + " is overloaded. Movement restricted.");
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}