package otus.atm;

import java.util.Set;
import java.util.TreeSet;

class InitialATMState {

    private final Set<Cell> cells;

    InitialATMState(Set<Cell> cells) {
        this.cells = copy(cells);
    }

    Set<Cell> getState() {
        return copy(cells);
    }

    private static Set<Cell> copy(Set<Cell> cells) {
        Set<Cell> innerCells = new TreeSet<>();

        for (Cell cell : cells) {
            innerCells.add(new Cell(cell.getNominal(), cell.getCount()));
        }

        return innerCells;
    }
}