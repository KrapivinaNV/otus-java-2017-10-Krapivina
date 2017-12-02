package otus;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.TestOnly;
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
                long cellCount = Math.min(Math.round(sum / cell.getNominal().getNote()), cell.getCount());
                if (cellCount != 0) {
                    resultMap.put(cell.getNominal(), cellCount);
                    sum -= cellCount * cell.getNominal().getNote();
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

    @TestOnly
    Set<Cell> getCells() {
        Set<Cell> cells = new TreeSet<>(Comparator.reverseOrder());
        cells.addAll(this.cells);

        return cells;
    }
}
