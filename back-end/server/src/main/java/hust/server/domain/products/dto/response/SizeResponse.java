package hust.server.domain.products.dto.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class SizeResponse {
    private Long id;
    private String size;
    private String price;
    private Integer isDefault;
}