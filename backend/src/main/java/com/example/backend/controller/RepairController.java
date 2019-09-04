package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RepairController {

    @Autowired
    private RepairDao repairDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private LongRentDao longRentDao;

    @Autowired
    private ShortRentDao shortRentDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private WorkOrderDao workOrderDao;

    @Autowired
    private WorkerDao workerDao;

    /**
     * 需要传入以下字段： tenant_id: String long_rent_id: String short_rent_id: String
     * 有一个可以是空 images: String[] description: String
     * 
     * @param request
     * @return
     */
    @PostMapping("add_repair")
    public String addRepair(@RequestBody String request) {
        Repair repair = JSONObject.parseObject(request, Repair.class);
        repair.setHaveWordOrder(false);
        repair.setWorkOrderId(null);
        repairDao.saveRepair(repair);
        return "success";
    }

    @PostMapping("delete_repair")
    public String deleteRepair(@RequestBody String request) {
        repairDao.deleteRepair(JSONObject.parseObject(request).getString("id"));
        return "success";
    }

    @PostMapping("create_order_for_repair")
    public String createOrderForRepair(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.parseObject(request);
        repairDao.UpdateWorkOrder(jsonObject.getString("id"), jsonObject.getString("work_order_id"));
        return "success";
    }

    @GetMapping("get_all_repairs")
    public List<JSONObject> getAllRepairs() {
        List<Repair> repairs = repairDao.getAllRepairs();
        List<JSONObject> result = new ArrayList<>();
        for (Repair repair : repairs) {
            result.add(fillRepairWithRoomAndTenant(repair));
        }
        return result;
    }

    @GetMapping("get_all_repairs_by_tenant_id")
    public List<JSONObject> getAllRepairsByTenantId(HttpServletRequest request) {
        List<Repair> repairs = repairDao.getAllRepairsByTenantId(request.getParameter("tenant_id"));
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (Repair repair : repairs) {
            jsonObjects.add(fillRepairWithRoomAndTenant(repair));
        }
        return jsonObjects;
    }

    private JSONObject fillRepairWithRoomAndTenant(Repair repair) {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(repair));
        String tenantID = repair.getTenantId();
        Tenant tenant = tenantDao.getTenantById(tenantID);
        if (tenant == null) {
            tenant = new Tenant();
        }
        jsonObject.put("tenant_name", tenant.getName());
        jsonObject.put("tenant_email", tenant.getEmail());
        String roomId = null;
        if (repair.getLongRentId() == null || repair.getLongRentId().equals("")) {
            roomId = shortRentDao.getShortRentById(repair.getShortRentId()).getRoomId();
        } else {
            roomId = longRentDao.getLongRentById(repair.getLongRentId()).getRoomId();
        }
        Room room = roomDao.getRoomById(roomId);
        if (room == null) {
            room = new Room();
        }
        jsonObject.put("address", room.getCity() + room.getLocation());
        if (repair.isHaveWordOrder() && repair.getWorkOrderId() != null) {
            jsonObject.put("isCompleted", workOrderDao.getWorkOrderByRepairId(repair.getId()).isIs_complete());
        } else {
            jsonObject.put("isCompleted", false);
        }
        if (repair.getWorkOrderId() != null && !repair.getWorkOrderId().equals("")) {
            Worker worker = workerDao
                    .getWorkerById(workOrderDao.getWorkOrderById(repair.getWorkOrderId()).getWorkerId());
            if (worker != null) {
                jsonObject.put("worker_name", worker.getName());
            }
        } else {
            jsonObject.put("worker_name", "");
        }
        return jsonObject;
    }

}
