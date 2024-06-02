package hust.server.domain.products.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestProductResponse {
    private Long id;
    private String name;
    private String price;
    private String summary;
    private String img;
}
