package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/long_rent")
public class LongRentController {

    @Autowired
    private LongRentDao longRentDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private TenantDao tenantDao;

    @PostMapping("/save")
    public String saveLongRentController(@RequestBody String request) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        LongRent longRent = new LongRent();
        String tenant_id = new org.json.JSONObject(request).getString("tenant_id");
        String room_id = new org.json.JSONObject(request).getString("room_id");
        if (!roomDao.isAvailable(room_id)) {
            return "failed";
        }
        try {

            Date start_time = sdf.parse(new org.json.JSONObject(request).getString("start_time"));
            Date end_time = sdf.parse(new org.json.JSONObject(request).getString("end_time"));

            // 从参数中获取
            longRent.setTenantId(tenant_id);
            longRent.setRoomId(room_id);
            longRent.setStartDate(start_time);
            longRent.setEndDate(end_time);

            // 后端设置默认值
            longRent.setPass(null);
            longRent.setReview(null);
            longRent.setEnd(false);
            longRent.setContract(false);

            // 计算payed数组
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(start_time);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(end_time);

            int yearGap = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
            int monthGap = endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
            int dayGap = endCal.get(Calendar.DATE) - startCal.get(Calendar.DATE);
            int monthDiff = yearGap * 12 + monthGap;
            if (dayGap > 0)
                monthDiff += 1;

            ArrayList<Boolean> payed = new ArrayList<Boolean>();
            for (int i = 0; i < monthDiff; i++)
                payed.add(Boolean.FALSE);
            longRent.setPayed(payed);

            longRentDao.saveLongRent(longRent);
            return "success";
        } catch (ParseException e) {
            return "failure";
        }
    }

    @PostMapping("/delete")
    public String deleteLongRentController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        roomDao.updateAvailable(longRentDao.getLongRentById(id).getRoomId(), true);
        longRentDao.deleteLongRent(id);
        return "success";
    }

    // Boolean初始化方式,不是boolean
    @PostMapping("/update/pass")
    public String updatePassController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        Boolean pass = new org.json.JSONObject(request).getBoolean("pass");
        longRentDao.updatePass(id, pass);
        roomDao.updateAvailable(longRentDao.getLongRentById(id).getRoomId(), !pass);
        return "success";
    }

    @PostMapping("/update/review")
    public String updateReviewController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        String review = new org.json.JSONObject(request).getString("review");
        longRentDao.updateReview(id, review);
        return "success";
    }

    @PostMapping("/update/payed")
    public String updatePayedController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        boolean payed = Boolean.parseBoolean(new org.json.JSONObject(request).getString("payed"));
        int num = Integer.parseInt(new org.json.JSONObject(request).getString("num"));
        longRentDao.updatePayed(id, payed, num);
        return "success";
    }

    @PostMapping("/renew")
    public JSONObject renewController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        int num = Integer.parseInt(new org.json.JSONObject(request).getString("num"));
        longRentDao.renew(id, num);
        longRentDao.updateIsRenew(id, true);
        return transLongRent(longRentDao.getLongRentById(id));
    }

    @PostMapping("/update/end")
    public String updateEndController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        boolean end = Boolean.parseBoolean(new org.json.JSONObject(request).getString("end"));
        longRentDao.updateEnd(id, end);
        roomDao.updateAvailable(longRentDao.getLongRentById(id).getRoomId(), true);
        return "success";
    }

    @GetMapping("/get/id")
    public LongRent getLongRentByIdController(HttpServletRequest request) {
        String id = request.getParameter("id");
        return longRentDao.getLongRentById(id);
    }

    @GetMapping("/get/tenant_id")
    public List<LongRent> getLongRentsByTenantIdController(HttpServletRequest request) {
        String tenantId = request.getParameter("tenant_id");
        return longRentDao.getLongRentsByTenantId(tenantId);
    }

    @GetMapping("/get/room_id")
    public List<LongRent> getLongRentsByRoomIdController(HttpServletRequest request) {
        String roomId = request.getParameter("room_id");
        return longRentDao.getLongRentsByRoomId(roomId);
    }

    @GetMapping("/get_all_long_rents")
    public List<JSONObject> getAllLongRents() {
        List<JSONObject> result = new ArrayList<>();
        result.clear();
        List<LongRent> longRents = longRentDao.getAllLongRents();
        for (LongRent longRent : longRents) {
            result.add(transLongRent(longRent));
        }
        return result;
    }

    @GetMapping("/get_all_contract_by_tenant_name_blurry")
    public List<JSONObject> getAllContractByTenantNameBlurry(@RequestParam String name) {
        List<Tenant> tenants = tenantDao.getAllTenantsBlurry(name);
        List<String> tenantIds = new ArrayList<>();
        for (Tenant tenant : tenants) {
            tenantIds.add(tenant.getId());
        }
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (String tenantId : tenantIds) {
            List<LongRent> longRents = longRentDao.getLongRentsByTenantId(tenantId).stream()
                    .filter(longRent -> (longRent.getPass() != null && longRent.getPass()))
                    .collect(Collectors.toList());
            List<JSONObject> result = new ArrayList<>();
            result.clear();
            for (LongRent longRent : longRents) {
                result.add(transLongRent(longRent));
            }
            jsonObjects.addAll(result);
        }
        return jsonObjects;
    }

    @GetMapping("/get_all_passed_long_rents")
    public List<LongRent> getAllPassedRents() {
        return longRentDao.getAllPassedLongRents();
    }

    @GetMapping("get_long_rent_and_their_room_by_id")
    public List<JSONObject> getShortRentAndTheirRoomById(HttpServletRequest request) {
        String tenantId = request.getParameter("tenant_id");
        List<LongRent> longRents = longRentDao.getLongRentsByTenantId(tenantId);
        List<JSONObject> result = new ArrayList<>();
        result.clear();
        for (LongRent longRent : longRents) {
            result.add(transLongRent(longRent));
        }
        return result;
    }

    @PostMapping("update_contract")
    public String updateContract(@RequestBody String json) {
        String id = JSONObject.parseObject(json).getString("long_rent_id");
        longRentDao.updateContract(id, true);
        return "success";
    }

    private JSONObject transLongRent(LongRent longRent) {
        JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(longRent));
        jsonObject.put("startDate", longRent.getStartDate());
        jsonObject.put("endDate", longRent.getEndDate());

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(longRent.getStartDate());
        endCalendar.setTime(longRent.getStartDate());
        endCalendar.add(Calendar.MONTH, 1);
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date());
        int currentMonthNum = 1;
        while (currentMonthNum <= longRent.getPayed().size()) {
            if (startCalendar.compareTo(currentCalendar) >= 0) {
                currentMonthNum = 0;
                break;
            }
            if (startCalendar.compareTo(currentCalendar) <= 0 && currentCalendar.compareTo(endCalendar) <= 0) {
                break;
            } else {
                startCalendar.add(2, 1);
                endCalendar.add(2, 1);
                currentMonthNum++;
            }
        }
        jsonObject.put("currentMonthNum", currentMonthNum);

        String roomId = longRent.getRoomId();
        Room room = roomDao.getRoomById(roomId);
        if (room == null) {
            room = new Room();
        }
        jsonObject.put("city", room.getCity());
        jsonObject.put("location", room.getLocation());
        jsonObject.put("address", room.getCity() + room.getLocation());
        jsonObject.put("longPrice", room.getLongPrice());
        switch (room.getType()) {
        case 1:
            jsonObject.put("type", "一人间");
            break;
        case 2:
            jsonObject.put("type", "二人间");
            break;
        case 4:
            jsonObject.put("type", "四人间");
        default:
            jsonObject.put("type", "其他");
            break;
        }
        if (longRent.getPass() == null) {
            jsonObject.put("status", "未审核");
        } else if (longRent.getPass()) {
            jsonObject.put("status", "通过");
        } else {
            jsonObject.put("status", "未通过");
        }
        if (longRent.isContract()) {
            jsonObject.put("contractStatus", "已签署");
        } else {
            jsonObject.put("contractStatus", "未签署");
        }
        Tenant tenant = tenantDao.getTenantById(longRent.getTenantId());
        if (tenant == null) {
            tenant = new Tenant();
        }
        jsonObject.put("tenant_email", tenant.getEmail());
        jsonObject.put("tenant_name", tenant.getName());
        return jsonObject;
    }

    @GetMapping("/is_renew")
    public boolean getIsRenewController(HttpServletRequest request) {
        String id = request.getParameter("_id");
        return longRentDao.getIsRenew(id);
    }

}
