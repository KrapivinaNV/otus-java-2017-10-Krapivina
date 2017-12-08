package otus;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import otus.exceptions.MyExceptions.NotEnoughMoneyExeption;

import java.util.*;
import java.util.stream.Collectors;

import static otus.exceptions.MyExceptions.CellNotFoundException;
import static otus.exceptions.MyExceptions.NoSuitableNotesException;

class ATM implements BankingOperationsAware {

    private Set<Cell> cells;

    ATM() {
        cells = new TreeSet<>(Comparator.reverseOrder());
        cells.addAll(Arrays.stream(Nominal.values()).map(nominal -> new Cell(nominal, 0L)).collect(Collectors.toSet()));
    }

    @Override
    public void initATM(Map<Nominal, Long> listCells) {
        Preconditions.checkNotNull(listCells);

        listCells.forEach((Nominal nominal, Long amount) -> {
                    cells.stream()
                            .filter(cell -> cell.getNominal().equals(nominal))
                            .findAny()
                            .orElseThrow(CellNotFoundException::new)
                            .refreshCount(amount);
                }
        );
    }

    @Override
    public void deposit(Map<Nominal, Long> listForDeposit) {
        Preconditions.checkNotNull(listForDeposit);

        listForDeposit.forEach(
                (nominal, aLong) -> cells.stream()
                        .filter(cell -> cell.getNominal() == nominal)
                        .findAny()
                        .ifPresent(cell -> cell.deposit(aLong))
        );
    }

    @Override
    public Map<Nominal, Long> withdraw(long sum) throws NotEnoughMoneyExeption, NoSuitableNotesException {
        if (this.getBalance() < sum) {
            throw new NotEnoughMoneyExeption();
        }

        Map<Nominal, Long> resultMap = new HashMap<>();
        for (Cell cell : cells) {
            if ((cell.getCount() != 0) && (sum >= cell.getNominal().getNote())) {
                long withdrawCount = Math.min(Math.round(sum / cell.getNominal().getNote()), cell.getCount());
                if (withdrawCount != 0) {
                    resultMap.put(cell.getNominal(), withdrawCount);
                    sum -= withdrawCount * cell.getNominal().getNote();
                    cell.refreshCount(cell.getCount() - withdrawCount);
                }
            }
        }
        if (sum != 0) {
            throw new NoSuitableNotesException();
        }
        return resultMap;
    }

    @Override
    public Long getBalance() {
        return cells.stream().mapToLong(Cell::getCellBalance).sum();
    }

    @Override
    public InitialATMState saveState() {
        return new InitialATMState(this, cells);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ATM atm = (ATM) object;
        return Objects.equal(cells, atm.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cells);
    }

    void setCells(Set<Cell> cells) {
        this.cells = cells;
    }


    static class InitialATMState {

        private Set<Cell> cells;
        private ATM atm;

        InitialATMState(ATM atm, Set<Cell> cells) {
            this.atm = atm;
            this.cells = cells;

        }

        void restore() {
            atm.setCells(this.cells);
        }
    }
}
