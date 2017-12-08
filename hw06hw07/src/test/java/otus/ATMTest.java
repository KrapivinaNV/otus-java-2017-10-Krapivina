package otus;

import com.google.common.collect.ImmutableMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import otus.exceptions.MyExceptions.NoSuitableNotesException;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static otus.exceptions.MyExceptions.NotEnoughMoneyExeption;

public class ATMTest {

    @Test(dataProvider = "withdrawDataProvider")
    public void withdraw(Map<Nominal, Long> initCells, long withdrawSum, Map<Nominal, Long> expected) {
        BankingOperationsAware atm = createATM(initCells);
        Map<Nominal, Long> withdrawResult = atm.withdraw(withdrawSum);

        assertEquals(expected, withdrawResult);
    }

    @DataProvider
    private Object[][] withdrawDataProvider() {
        return new Object[][]{
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L),
                        5200L,
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 2L),
                },
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L),
                        1200L,
                        ImmutableMap.of(Nominal.THOUSANDS, 1L, Nominal.HUNDREDS, 2L),
                }
        };
    }

    @Test(expectedExceptions = NoSuitableNotesException.class)
    public void withdrawNoSuitableNotes() {
        BankingOperationsAware atm = createATM(ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 0L, Nominal.HUNDREDS, 2L, Nominal.THOUSANDS, 50L));
        Map<Nominal, Long> withdrawResult = atm.withdraw(1300);
    }

    @Test(expectedExceptions = NotEnoughMoneyExeption.class)
    public void withdrawNotEnoughtMoney() {
        BankingOperationsAware atm = createATM(ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 0L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L));
        Map<Nominal, Long> withdrawResult = atm.withdraw(110000);
    }


    @Test(dataProvider = "depositDataProvider")
    public void deposit(Map<Nominal, Long> initCells, Map<Nominal, Long> deposit, BankingOperationsAware expected) {
        BankingOperationsAware atm = createATM(initCells);
        atm.deposit(deposit);

        assertEquals(expected, atm);
    }

    @DataProvider
    private Object[][] depositDataProvider() {
        return new Object[][]{
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L),
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 1L),
                        createATM(
                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 2L, Nominal.HUNDREDS, 11L, Nominal.THOUSANDS, 50L)
                        ),
                },
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L),
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 10L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L),
                        createATM(
                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 11L, Nominal.HUNDREDS, 20L, Nominal.THOUSANDS, 100L)
                        ),
                },
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L),
                        ImmutableMap.of(),
                        createATM(
                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L)
                        ),
                },
                {
                        ImmutableMap.of(),
                        ImmutableMap.of(),
                        createATM(
                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 0L, Nominal.HUNDREDS, 0L, Nominal.THOUSANDS, 0L)
                        ),
                },
                {
                        ImmutableMap.of(),
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L),
                        createATM(
                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 10L, Nominal.THOUSANDS, 50L)
                        ),
                },

        };
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void depositError() {
        BankingOperationsAware atm = createATM(ImmutableMap.of());
        atm.deposit(null);
    }

    @Test(dataProvider = "getBalanceDataProvider")
    public void getBalance(Map<Nominal, Long> initCells, long expected) {
        BankingOperationsAware atm = createATM(initCells);
        assertEquals(Long.valueOf(expected), atm.getBalance());
    }

    @DataProvider
    private Object[][] getBalanceDataProvider() {
        return new Object[][]{
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 1L, Nominal.THOUSANDS, 5L),
                        10100
                },
                {
                        ImmutableMap.of(),
                        0
                }

        };
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void atmInitError() {
        BankingOperationsAware atm = createATM(null);
    }

    private BankingOperationsAware createATM(Map<Nominal, Long> initCells) {
        BankingOperationsAware atm = new ATM();
        atm.initATM(initCells);
        return atm;
    }
}
