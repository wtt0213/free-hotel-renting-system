package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.model.Notice;
import com.example.backend.model.NoticeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeDao noticeDao;

    //默认url中可以获取所有Notice属性
    @PostMapping("/save_notice")
    public String saveNoticeController(@RequestBody String request){
        Notice notice = JSONObject.parseObject(request, Notice.class);
        notice.setRead(false);
        noticeDao.saveNotice(notice);
        return "success";
    }

    @PostMapping("/delete_notice")
    public String deleteNoticeController(@RequestBody String request){
        String id = new org.json.JSONObject(request).getString("id");
        noticeDao.deleteNotice(id);
        return "success";
    }

    @PostMapping("/update_type")
    public String updateTypeController(@RequestBody String request){
        String type = new org.json.JSONObject(request).getString("type");
        noticeDao.updateType(type);
        return "success";
    }

    @PostMapping("/update_content")
    public String updateContentController(@RequestBody String request){
        String content = new org.json.JSONObject(request).getString("content");
        noticeDao.updateContent(content);
        return "success";
    }
    @GetMapping("/get_by_tenant_id")
    public List<Notice> getNoticesByTenantIdController(HttpServletRequest request){
        String tenantId = request.getParameter("tenant_id");
        return noticeDao.getNoticesByTenantId(tenantId);
    }

    @PostMapping("/set_read")
    public String setNoticeRead(@RequestBody String json){
        noticeDao.setRead(JSONObject.parseObject(json).getString("notice_id"));
        return "success";
    }
}
