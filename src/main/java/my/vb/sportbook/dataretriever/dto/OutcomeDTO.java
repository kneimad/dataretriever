package my.vb.sportbook.dataretriever.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.vb.sportbook.dataretriever.model.Outcome;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private Long id;
    private String description;
    private boolean settled;
    private BigDecimal price;
    private String result;

    public OutcomeDTO(Outcome outcome) {
        this.id = outcome.getId();
        this.description = outcome.getDescription();
        this.settled = outcome.isSettled();
        this.price = outcome.getPrice();
        this.result = outcome.getResult();
    }

    public Outcome convertTo(){
        return Outcome.builder()
                .id(id)
                .description(description)
                .settled(settled)
                .price(price)
                .result(result)
                .build();
    }
}
