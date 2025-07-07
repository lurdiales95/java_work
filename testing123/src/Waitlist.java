public class Waitlist {
    private Reservation[] reservations;
    private int currentIndex = 0;

    public Waitlist(int defaultSize) {
        reservations = new Reservation[defaultSize];
    }

    public void add(Reservation r) {
        reservations[currentIndex] = r;
        currentIndex++;

        if (currentIndex >= reservations.length) {
            growWaitlist();
        }
    }

    public void growWaitlist() {
        Reservation[] temp = new Reservation[reservations.length * 2];

        for (int i = 0; i < reservations.length; i++) {
            temp[i] = reservations[i];
        }

        reservations = temp;
    }
}
