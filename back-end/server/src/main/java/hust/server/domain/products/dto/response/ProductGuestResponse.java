package hust.server.domain.products.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductGuestResponse {
    private Long id;
    private String name;
    private Long price;
    private String summary;
    private String img;
}
