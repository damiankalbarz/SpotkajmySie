import java.util.ArrayList;
import java.util.List;

public class Main {

    //zmiana minut na format "hh:mm"
    public static String convertMinutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    //zamiana i przeliczenie godziny na liczbe minut
    public static int convertToMinutes(String hour) {
        String[] parts1 = hour.split(":");
        int hour1 = Integer.parseInt(parts1[0]);
        int min1 = Integer.parseInt(parts1[1]);
        return hour1 * 60 + min1;
    }

    public static List<TimeRange> findAvailableMeetings(Calendar calendar1, Calendar calendar2, int meetingDuration) {
        List<TimeRange> availableMeetings = new ArrayList<>();
        int freetime;
        int start;
        int end;

        //Dodanie na poczatek listy plannedMettings czasu rozpaczecia pracy
        calendar1.getPlannedMeetings().add(0, new TimeRange(calendar1.getWorkingHours().getStart(), calendar1.getWorkingHours().getStart()));
        calendar2.getPlannedMeetings().add(0, new TimeRange(calendar2.getWorkingHours().getStart(), calendar2.getWorkingHours().getStart()));

        //Dodanie na koniec listy plannedMettings czasu zakończenia pracy
        calendar1.getPlannedMeetings().add(new TimeRange(calendar1.getWorkingHours().getEnd(), calendar1.getWorkingHours().getEnd()));
        calendar2.getPlannedMeetings().add(new TimeRange(calendar2.getWorkingHours().getEnd(), calendar2.getWorkingHours().getEnd()));


        for (int i = 1; i < calendar1.getPlannedMeetings().size(); i++) {
            freetime = convertToMinutes(calendar1.getPlannedMeetings().get(i).getStart()) - convertToMinutes(calendar1.getPlannedMeetings().get(i - 1).getEnd());
            if (freetime >= meetingDuration) { //sprawdzenie czy okienko miedzy spotkaniami wynosi co najmniej meetingDuration (30 min)
                for (int j = 1; j < calendar2.getPlannedMeetings().size(); j++) {
                    //freetime = convertToMinutes(calendar2.getPlannedMeetings().get(j).getStart()) - convertToMinutes(calendar2.getPlannedMeetings().get(j - 1).getEnd());
                    //if (freetime >= meetingDuration) { //sprawdzenie czy okienko miedzy spotkaniami wynosi co najmniej meetingDuration (30 min)
                    start = Math.max(convertToMinutes(calendar2.getPlannedMeetings().get(j - 1).getEnd()), convertToMinutes(calendar1.getPlannedMeetings().get(i - 1).getEnd()));
                    end = Math.min(convertToMinutes(calendar2.getPlannedMeetings().get(j).getStart()), convertToMinutes(calendar1.getPlannedMeetings().get(i).getStart()));
                    if (end - start >= meetingDuration) { //sprawdzenie czy dostępny czas na spotkanie dla dwoch kalendarzy wynosi co najmniej  meetingDuration (30 min)
                        TimeRange timeRange = new TimeRange(convertMinutesToTime(start), convertMinutesToTime(end));
                        availableMeetings.add(timeRange);
                    }
                    //}
                }
            }
        }

        return availableMeetings;
    }


    public static void main(String[] args) {
        Calendar calendar1 = new Calendar(new TimeRange("09:00", "19:55"), new ArrayList<>(List.of(
                new TimeRange("09:00", "10:30"),
                new TimeRange("12:00", "13:00"),
                new TimeRange("16:00", "18:00"))
        ));

        Calendar calendar2 = new Calendar(new TimeRange("10:00", "18:30"), new ArrayList<>(List.of(
                new TimeRange("10:00", "11:30"),
                new TimeRange("12:30", "14:30"),
                new TimeRange("14:30", "15:00"),
                new TimeRange("16:00", "17:00"))
        ));

        List<TimeRange> availableMeetings = findAvailableMeetings(calendar1, calendar2, 30);

        System.out.println(availableMeetings);
    }
}
