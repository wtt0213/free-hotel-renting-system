package com.example.backend.model;

import java.util.List;

public interface ComplaintDao {
    void saveComplaint(Complaint complaint);

    void deleteComplaint(String id);

    void updateReview(String id, String review);

    Complaint getComplaintById(String id);

    List<Complaint> getComplaintByTenantId(String tenantId);

    List<Complaint> getComplaintByRoomId(String roomId);

    List<Complaint> getAllComplaint();
}
