package otus;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class AtmDepartmentTest {

    @Test(dataProvider = "getBalanceDataProvider")
    public void getBalance(Map<Nominal, Long> initCells, int count, long expected) {
        ArrayList<BankingOperationsAware> atms = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            atms.add(createATM(initCells));
        }
        AtmDepartment atmDepartment = new AtmDepartment(atms);
        assertEquals(Long.valueOf(expected), atmDepartment.getBalance());
    }

    @DataProvider
    private Object[][] getBalanceDataProvider() {
        return new Object[][]{
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L),
                        2,
                        10600
                },
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L),
                        3,
                        15900
                },
                {
                        ImmutableMap.of(),
                        1,
                        0
                }

        };
    }


    @Test(dataProvider = "saveATMsStateDataProvider")
    public void saveATMsState(Map<Nominal, Long> initCells_one, Map<Nominal, Long> initCells_two, AtmDepartment expected) {
        ArrayList<BankingOperationsAware> atms = new ArrayList<>();

        atms.add(createATM(initCells_one));
        atms.add(createATM(initCells_two));

        AtmDepartment atmDepartment = new AtmDepartment(atms);

        atms.get(0).withdraw(100);
        atms.get(1).deposit(ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L));

        atmDepartment.restoreStateATM();

        assertEquals(expected, atmDepartment);
    }

    @DataProvider
    private Object[][] saveATMsStateDataProvider() {
        return new Object[][]{
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L),
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L),
                        new AtmDepartment(
                                ImmutableList.of(
                                        createATM(
                                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L)
                                        ),
                                        createATM(
                                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L
                                                )
                                        )
                                )
                        )
                },
                {
                        ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L),
                        ImmutableMap.of(),
                        new AtmDepartment(
                                ImmutableList.of(
                                        createATM(
                                                ImmutableMap.of(Nominal.FIVE_THOUSANDTH, 1L, Nominal.HUNDREDS, 3L, Nominal.THOUSANDS, 0L)
                                        ),
                                        createATM(
                                                ImmutableMap.of()
                                        )
                                )
                        )
                }
        };
    }

    private BankingOperationsAware createATM(Map<Nominal, Long> initCells) {
        BankingOperationsAware atm = new ATM();
        atm.initATM(initCells);
        return atm;
    }
}
