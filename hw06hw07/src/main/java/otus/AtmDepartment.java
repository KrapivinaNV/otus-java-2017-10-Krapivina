package otus;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtmDepartment {

    private List<BankingOperationsAware> atmList = new ArrayList<>();
    private List<ATM.InitialATMState> initialATMStates = new ArrayList<>();

    AtmDepartment(List<BankingOperationsAware> atms) {
        this.atmList.addAll(atms);
        saveStateATM();
    }

    Long getBalance() {
        return atmList.stream().mapToLong(BankingOperationsAware::getBalance).sum();
    }

    private void saveStateATM() {
        initialATMStates = atmList.stream().map(BankingOperationsAware::saveState).collect(Collectors.toList());
    }

    void restoreStateATM() {
        if (initialATMStates != null) {
            initialATMStates.forEach(ATM.InitialATMState::restore);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AtmDepartment that = (AtmDepartment) object;
        return Objects.equal(atmList, that.atmList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(atmList);
    }
}