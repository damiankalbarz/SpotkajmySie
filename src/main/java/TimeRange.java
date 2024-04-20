import lombok.*;

@Data
@AllArgsConstructor
public class TimeRange {
    private String start;
    private String end;

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }
}
