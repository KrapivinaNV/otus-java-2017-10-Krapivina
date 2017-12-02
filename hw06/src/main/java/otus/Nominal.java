package otus;

enum Nominal {
    HUNDREDS(100),
    THOUSANDS(1000),
    FIVE_THOUSANDTH(5000);

    private long note;

    Nominal(long note) {
        this.note = note;
    }

    public long getNote() {
        return note;
    }
}
