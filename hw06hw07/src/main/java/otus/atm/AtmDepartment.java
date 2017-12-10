package otus.atm;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

public class AtmDepartment {

    private List<BankingOperationsAware> atmList;

    AtmDepartment(List<BankingOperationsAware> atms) {
        this.atmList = atms;
    }

    Long getBalance() {
        return atmList.stream().mapToLong(BankingOperationsAware::getBalance).sum();
    }


    void restoreStateATM() {
        atmList.forEach(BankingOperationsAware::restoreState);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AtmDepartment that = (AtmDepartment) object;
        return Objects.equal(atmList, that.atmList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(atmList);
    }
}