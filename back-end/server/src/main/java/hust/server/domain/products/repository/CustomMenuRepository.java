package hust.server.domain.products.repository;

import hust.server.domain.products.dto.response.MenuGuestResponse;

import java.util.List;

public interface CustomMenuRepository {
    public List<Object> getMenuProducts(Long branchId);
}
