package br.jonasdsg.b3cs.dto;

import java.util.List;

public class YearDTO {
    public String year;
    public List<MonthDTO> table;

    @Override
    public String toString() {
        return "{" +
            " year='" + year + "'" +
            ", table='" + table + "'" +
            "}";
    }

}
