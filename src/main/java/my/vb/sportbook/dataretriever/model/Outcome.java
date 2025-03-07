package my.vb.sportbook.dataretriever.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Outcome implements IndexedEntity {
    private Long id;
    private String description;
    private boolean settled;
    private BigDecimal price;
    private String result;
}
