package br.jonasdsg.b3cs.dto;

import java.util.List;

public class HistoryDTO {
    public String code;
    public List<YearDTO> years;

    @Override
    public String toString() {
        return "{" +
            " code='" + code + "'" +
            ", years='" + years + "'" +
            "}";
    }

}
