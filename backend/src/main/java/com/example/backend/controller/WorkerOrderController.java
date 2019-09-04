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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class WorkerOrderController {

    @Autowired
    private WorkOrderDao workOrderDao;

    @Autowired
    private RepairDao repairDao;

    @Autowired
    private LongRentDao longRentDao;

    @Autowired
    private ShortRentDao shortRentDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private WorkerDao workerDao;

    /**
     * 只需要传入两个参数 一个是师傅的id，worker_id 一个是投诉类的id, repair_id
     * 
     * @param request
     * @return 请注意！保存工单的时候回返回工单的id，键名是 work_order_id
     */
    @PostMapping("add_work_order")
    public Map<String, Object> addWorkOrder(@RequestBody String request) {
        WorkOrder workOrder = JSONObject.parseObject(request, WorkOrder.class);
        workOrder.setIs_complete(false);
        workOrder.setMark(5.0);
        workOrder.setReview(null);
        workOrder = workOrderDao.saveWorkOrder(workOrder);
        repairDao.UpdateWorkOrder(workOrder.getRepairId(), workOrder.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("work_order_id", workOrder.getId());
        return result;
    }

    @PostMapping("delete_work_order")
    public String deleteWorkOrder(@RequestBody String request) {
        String id = JSONObject.parseObject(request).getString("id");
        workOrderDao.deleteWorkOrder(id);
        return "success";
    }

    @GetMapping("get_all_work_orders")
    public List<JSONObject> getAllWorkOrders() {
        List<WorkOrder> workOrders = workOrderDao.getAllWorkOrders();
        return fillWorkOrders(workOrders);
    }

    @GetMapping("get_all_work_orders_By_worker_Id")
    public List<JSONObject> getAllWorkOrdersByWorkerId(HttpServletRequest request) {
        List<WorkOrder> workOrders = workOrderDao.getAllWorkOrdersByWorkerId(request.getParameter("worker_id"));
        return fillWorkOrders(workOrders);
    }

    private List<JSONObject> fillWorkOrders(List<WorkOrder> workOrders) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (WorkOrder workOrder : workOrders) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(workOrder));
            jsonObject.putAll(fillRepairWithRoomAndTenant(repairDao.getRepairById(workOrder.getRepairId())));
            jsonObjects.add(jsonObject);
            jsonObject.put("id", workOrder.getId());
            jsonObject.put("worker_id", workerDao.getWorkerById(workOrder.getWorkerId()).getName());
        }
        return jsonObjects;
    }

    @PostMapping("complete_work_order")
    public String completeWorkOrder(@RequestBody String request) {
        String id = JSONObject.parseObject(request).getString("id");
        workOrderDao.completeWorkOrder(id);
        return "success";
    }

    /**
     * id: String 工单的id mark: String 评分 review：String
     * 
     * @param request
     * @return
     */
    @PostMapping("review_workerOrder")
    public String updateWorkOrder(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.parseObject(request);
        String id = jsonObject.getString("id");
        double mark = Double.parseDouble(jsonObject.getString("mark"));
        String review = jsonObject.getString("review");
        workOrderDao.reviewWorkOrder(id, mark, review);
        return "success";
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
        return jsonObject;
    }

    /**
     * repair_id: String
     * 
     * @param repair_id
     * @return 返回完整的WorkOrder对象
     */
    @GetMapping(value = "/get_work_order_by_repair_id")
    public WorkOrder getWordOrderByRepairId(@RequestParam String repair_id) {
        return workOrderDao.getWorkOrderByRepairId(repair_id);
    }

    @PostMapping("/mark_work_order")
    public String markWorkOrder(@RequestBody String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String workOrderId = jsonObject.getString("worker_order_id");
        double mark = Double.parseDouble(jsonObject.getString("mark"));
        workOrderDao.markWorkOrder(workOrderId, mark);
        WorkOrder workOrder = workOrderDao.getWorkOrderById(workOrderId);
        if (workOrder == null) {
            return "failed";
        }
        workerDao.updateMark(workOrder.getWorkerId(), mark);
        return "success";
    }

}
