package com.example.backend.controller;

import com.example.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/short_rent")
public class ShortRentController {

    @Autowired
    private ShortRentDao shortRentDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private TenantDao tenantDao;

    @PostMapping("/save")
    public ShortRent saveShortRentController(@RequestBody String request) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ShortRent shortrent = new ShortRent();
        String tenant_id = new org.json.JSONObject(request).getString("tenant_id");
        String room_id = new org.json.JSONObject(request).getString("room_id");
        if (!roomDao.isAvailable(room_id)) {
            return null;
        }
        try {
            Date start_time = sdf.parse(new org.json.JSONObject(request).getString("start_time"));
            Date end_time = sdf.parse(new org.json.JSONObject(request).getString("end_time"));

            // 从参数中获取
            shortrent.setTenantId(tenant_id);
            shortrent.setRoomId(room_id);
            shortrent.setStartDate(start_time);
            shortrent.setEndDate(end_time);

            // 后端设置默认值
            shortrent.setPass(null);
            shortrent.setReview(null);
            shortrent.setEnd(false);
            shortrent.setPayed(false);

            // 后端计算price
            int dayDiff = (int) ((end_time.getTime() - start_time.getTime()) / (24 * 60 * 60 * 1000));
            BigDecimal shortPrice = roomDao.getRoomById(room_id).getShortPrice();
            BigDecimal price = shortPrice.multiply(new BigDecimal(dayDiff));
            shortrent.setPrice(price);
            shortRentDao.saveShortRent(shortrent);
            return shortrent;
        } catch (ParseException e) {
            return null;
        }
    }

    @PostMapping("/delete")
    public String deleteShortRentController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        roomDao.updateAvailable(shortRentDao.getShortRentById(id).getRoomId(), true);
        shortRentDao.deleteShortRent(id);
        return "success";
    }

    // Boolean初始化方式,不是boolean
    @PostMapping("/update/pass")
    public String updatePassController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        Boolean pass = new org.json.JSONObject(request).getBoolean("pass");
        shortRentDao.updatePass(id, pass);
        roomDao.updateAvailable(shortRentDao.getShortRentById(id).getRoomId(), !pass);
        return "success";
    }

    @PostMapping("/update/review")
    public String updateReviewController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        String review = new org.json.JSONObject(request).getString("review");
        shortRentDao.updateReview(id, review);
        return "success";
    }

    @PostMapping("/update/payed")
    public String updatePayedController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        boolean payed = new org.json.JSONObject(request).getBoolean("payed");
        shortRentDao.updatePayed(id, payed);
        return "success";
    }

    @PostMapping("/update/end")
    public String updateEndController(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        boolean end = Boolean.parseBoolean(new org.json.JSONObject(request).getString("end"));
        shortRentDao.updateEnd(id, end);
        roomDao.updateAvailable(shortRentDao.getShortRentById(id).getRoomId(), true);
        return "success";
    }

    @GetMapping("/get/id")
    public ShortRent getShortRentByIdController(HttpServletRequest request) {
        String id = request.getParameter("id");
        return shortRentDao.getShortRentById(id);
    }

    @GetMapping("/get/tenant_id")
    public List<ShortRent> getShortRentsByTenantIdController(HttpServletRequest request) {
        String tenantId = request.getParameter("tenant_id");
        return shortRentDao.getShortRentsByTenantId(tenantId);
    }

    @GetMapping("get_short_rent_and_their_room_by_id")
    public List<JSONObject> getShortRentAndTheirRoomById(HttpServletRequest request) {
        String tenantId = request.getParameter("tenant_id");
        List<ShortRent> shortRents = shortRentDao.getShortRentsByTenantId(tenantId);
        List<JSONObject> result = new ArrayList<>();
        result.clear();
        for (ShortRent shortRent : shortRents) {
            result.add(transShortRent(shortRent));
        }
        return result;
    }

    @GetMapping("/get/room_id")
    public List<ShortRent> getShortRentsByRoomIdController(HttpServletRequest request) {
        String roomId = request.getParameter("room_id");
        return shortRentDao.getShortRentsByRoomId(roomId);
    }

    @GetMapping("/get_all_short_rents")
    public List<JSONObject> getAllShortRents() {
        List<ShortRent> shortRents = shortRentDao.getAllShortRents();
        List<JSONObject> result = new ArrayList<>();
        result.clear();
        for (ShortRent shortRent : shortRents) {
            result.add(transShortRent(shortRent));
        }
        return result;
    }

    @GetMapping("/get_all_passed_short_rents")
    public List<ShortRent> getAllPassedShortRents() {
        return shortRentDao.getAllPassedShortRents();
    }

    private JSONObject transShortRent(ShortRent shortRent) {
        JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(shortRent));
        jsonObject.put("startDate", shortRent.getStartDate());
        jsonObject.put("endDate", shortRent.getEndDate());
        String roomId = shortRent.getRoomId();
        Room room = roomDao.getRoomById(roomId);
        if (room != null) {
            jsonObject.put("city", room.getCity());
            jsonObject.put("location", room.getLocation());
            jsonObject.put("address", room.getCity() + room.getLocation());
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
            if (shortRent.getPass() == null) {
                jsonObject.put("status", "未审核");
            } else if (shortRent.getPass()) {
                jsonObject.put("status", "通过");
            } else {
                jsonObject.put("status", "未通过");
            }
            if (shortRent.isPayed()) {
                jsonObject.put("isPayed", "已缴费");
            } else {
                jsonObject.put("isPayed", "未缴费");
            }
            jsonObject.put("room_image_url", room.getHdpiImages().get(0));
        }
        Tenant tenant = tenantDao.getTenantById(shortRent.getTenantId());
        if (tenant != null) {
            jsonObject.put("tenant_email", tenant.getEmail());
            jsonObject.put("tenant_name", tenant.getName());
        }
        return jsonObject;
    }

}
