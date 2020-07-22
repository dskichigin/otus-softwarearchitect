package dsk.otus.softwarearchitect.task10.warehouse.object;

public enum TypeOperation {
    ARRIVAL("поступление"),
    RESERVE("резерв");

    private String name;
    TypeOperation(String name) {this.name = name;}

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }

}