package my.vb.sportbook.dataretriever.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.vb.sportbook.dataretriever.model.Market;
import my.vb.sportbook.dataretriever.model.StatusEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private Long id;
    private String description;
    private StatusEnum status;
    private boolean settled;
    private List<OutcomeDTO> outcomes;

    public MarketDTO(Market market) {
        this.id = market.getId();
        this.description = market.getDescription();
        this.status = market.getStatus();
        this.settled = market.isSettled();
        this.outcomes = market.getOutcomes().stream().map(OutcomeDTO::new).toList();
    }

}

