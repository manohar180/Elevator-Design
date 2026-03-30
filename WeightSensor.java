public class WeightSensor {
    private double limit;

    public WeightSensor(double limit) {
        this.limit = limit;
    }

    public boolean isOverloaded(double currentWeight) {
        return currentWeight > limit;
    }

    public void setLimit(double newLimit) {
        this.limit = newLimit;
    }

    public double getLimit() {
        return limit;
    }
}