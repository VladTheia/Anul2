import java.time.Clock;

class ClockTime {
    int hour, minute;

    public ClockTime(int h, int m) {
        hour = h;
        minute = m;
    }

    int equals(ClockTime t) {
        if (hour == t.hour && minute == t.minute)
            return 0;
        if ((hour == t.hour && minute > t.minute) || hour > t.hour)
            return 1;
        //if ((hour == t.hour && minute < t.minute) || hour < t.hour)
            return -1;
    }
}

public class Schedule {
    ClockTime departure;
    ClockTime arrival;

    public Schedule(ClockTime d, ClockTime a) {
        departure = d;
        arrival = a;
    }

    int durata() {
        return (arrival.hour * 60 + arrival.minute) - (departure.hour + departure.minute);
    }
}

class Route {
    String origin, destination;

    public Route(String o, String d) {
        origin = o;
        destination = d;
    }
//buc - ct ct - buc
    boolean isTourRetour(Route r) {
        if (origin.equals(r.destination) && destination.equals(r.origin))
            return true;
        return false;
    }
}

class Train {
    Route r;
    Schedule s;
    boolean local;
    static final int X = 1;

    int pret() {
        return X * s.durata();
    }

    public static void main(String[] args) {
        ClockTime c1 = new ClockTime(9, 35);
        ClockTime c2 = new ClockTime(12, 2);
        ClockTime c3 = new ClockTime(5, 45);
        ClockTime c4 = new ClockTime(12, 49);


    }

}
