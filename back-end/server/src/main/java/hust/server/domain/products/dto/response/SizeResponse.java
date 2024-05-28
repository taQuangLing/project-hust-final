package hust.server.domain.products.dto.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class SizeResponse {
    private int id;
    private String size;
    private Long price;
    private Integer isDefault;
}
