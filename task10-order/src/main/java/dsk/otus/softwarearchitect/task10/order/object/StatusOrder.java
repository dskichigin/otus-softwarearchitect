package dsk.otus.softwarearchitect.task10.order.object;

public enum StatusOrder {
    IN_WORK("в работе"),
    COMPLETE("завершен"),
    CANCELED("отменен");

    private String name;
    StatusOrder(String name) {this.name = name;}

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }

}