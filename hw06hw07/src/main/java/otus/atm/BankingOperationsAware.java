package otus.atm;

import java.util.Map;

public interface BankingOperationsAware {

    void initATM(Map<Nominal, Long> listCells);

    void deposit(Map<Nominal, Long> listForDeposit);

    Map<Nominal, Long> withdraw(long sum);

    Long getBalance();

    void restoreState();
}