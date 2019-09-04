package com.example.backend.model;

import java.util.List;

public interface ShortRentDao {

    void saveShortRent(ShortRent shortRent);

    void deleteShortRent(String id);

    void updatePass(String id, Boolean pass);

    void updateReview(String id, String review);

    void updatePayed(String id, boolean payed);

    void updateEnd(String id, boolean end);

    ShortRent getShortRentById(String id);

    List<ShortRent> getShortRentsByTenantId(String tenantId);

    List<ShortRent> getShortRentsByRoomId(String roomId);

    List<ShortRent> getAllShortRents();

    List<ShortRent> getAllPassedShortRents();
}
