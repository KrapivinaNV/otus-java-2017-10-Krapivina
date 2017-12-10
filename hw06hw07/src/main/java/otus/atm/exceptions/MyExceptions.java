package otus.atm.exceptions;

public class MyExceptions {
    public static class CellNotFoundException extends RuntimeException {
    }

    public static class NoSuitableNotesException extends RuntimeException {
    }

    public static class NotEnoughMoneyExeption extends RuntimeException {
    }
}
