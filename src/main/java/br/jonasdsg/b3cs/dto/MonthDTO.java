package br.jonasdsg.b3cs.dto;

public class MonthDTO {
    public String month;
    public String table;

    @Override
    public String toString() {
        return "{" +
            " month='" + month + "'" +
            ", table='" + table + "'" +
            "}";
    }

}
