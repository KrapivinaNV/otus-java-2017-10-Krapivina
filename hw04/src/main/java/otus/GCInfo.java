package otus;

public class GCInfo {

    private long duration;
    private long startTime;
    private long endTime;
    private TypeGeneration type;

    GCInfo(TypeGeneration type, long duration, long startTime, long endTime){
        this.type = type;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "GCInfo{" +
                "duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", type=" + type +
                '}';
    }
}

