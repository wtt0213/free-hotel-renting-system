package com.example.backend.model;

import java.util.List;

public interface NoticeDao {

    void saveNotice(Notice notice);

    void deleteNotice(String id);

    void updateType(String type);

    void updateContent(String content);

    List<Notice> getNoticesByTenantId(String tenantId);

    void setRead(String id);
}
