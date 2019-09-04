package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.model.Complaint;
import com.example.backend.model.ComplaintDao;
import com.example.backend.model.Tenant;
import com.example.backend.model.TenantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintDao complaintDao;

    @Autowired
    private TenantDao tenantDao;

    /**
     * 请求体中应该传入若干参数：
     * tenant_id: String
     * room_id: String
     * image: String[]
     * description: String
     * @param request
     * @return
     */
    @PostMapping("/save")
    public String saveComplaint(@RequestBody String request){
        JSONObject jsonObject = JSONObject.parseObject(request);
        String tenantId = jsonObject.getString("id");
        String description = jsonObject.getString("description");
        Complaint complaint = new Complaint();
        complaint.setTenantId(tenantId);
        complaint.setDescription(description);
        complaint.setIs_reviewed(false);
        complaintDao.saveComplaint(complaint);
        return "success";
    }

    @PostMapping("/delete")
    public String deleteComplaint(@RequestBody String request){
        String id = new org.json.JSONObject(request).getString("id");
        complaintDao.deleteComplaint(id);
        return "success";
    }

    @PostMapping("/update_review")
    public String updateReview(@RequestBody String request){
        String id = new org.json.JSONObject(request).getString("id");
        String review = new org.json.JSONObject(request).getString("review");
        complaintDao.updateReview(id, review);
        return "success";
    }

    @GetMapping("/get/id")
    Complaint getComplaintById(HttpServletRequest request) {
        String id = request.getParameter("id");
        return complaintDao.getComplaintById(id);
    }

    @GetMapping("/get_complaints_by_tenantId")
    List<Complaint> getComplaintByTenantId(HttpServletRequest request) {
        String tenantId = request.getParameter("tenant_id");
        return complaintDao.getComplaintByTenantId(tenantId);
    }

    @GetMapping("/get_complaint_By_roomId")
    List<Complaint> getComplaintByRoomId(HttpServletRequest request) {
        String roomId = request.getParameter("room_id");
        return complaintDao.getComplaintByRoomId(roomId);
    }

    @GetMapping("/get_all_complaint")
    List<JSONObject> getAllComplaint(){
        List<Complaint> complaints = complaintDao.getAllComplaint();
        List<JSONObject> result = new ArrayList<>();
        for (Complaint complaint : complaints){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(complaint));
            String tenantID = complaint.getTenantId();
            Tenant tenant = tenantDao.getTenantById(tenantID);
            if(tenant==null){
                jsonObject.put("tenant_name", "");
                jsonObject.put("tenant_email", "");
            } else {
                jsonObject.put("tenant_name", tenant.getName());
                jsonObject.put("tenant_email", tenant.getEmail());
            }
            result.add(jsonObject);
        }
        return result;
    }
}
