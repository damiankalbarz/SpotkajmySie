import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Calendar {
    private TimeRange workingHours;
    private List<TimeRange> plannedMeetings;

}
