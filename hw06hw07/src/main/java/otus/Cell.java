package otus;

import com.google.common.base.Objects;

public class Cell implements Comparable<Cell> {
    private Nominal nominal;
    private long count;


    Cell(Nominal nominal, Long count) {
        this.nominal = nominal;
        this.count = count;
    }

    void refreshCount(Long newCount){
        this.count = newCount;
    }


    void deposit(Long countToWithdraw) {
        this.count += countToWithdraw;
    }

    public void withdraw(Long countToDeposit) throws Exception {
        long result = this.count - countToDeposit;
        if (result < 0) {
            throw new Exception();
        }
        this.count = result;
    }

    long getCount() {
        return count;
    }

    Nominal getNominal() {
        return nominal;
    }

    long getCellBalance() {
        return nominal.getNote() * count;
    }

    @Override
    public int compareTo(Cell other) {
        return Long.compare( this.nominal.getNote(), other.nominal.getNote());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Cell cell = (Cell) object;
        return count == cell.count &&
                nominal == cell.nominal;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nominal, count);
    }
}