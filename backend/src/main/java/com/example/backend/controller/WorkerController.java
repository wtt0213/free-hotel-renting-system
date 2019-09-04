package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.model.Worker;
import com.example.backend.model.WorkerDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private WorkerDao workerDao;

    /**
     * 具体应该传入name，image，phone 以上字段均为String
     * 
     * @param request
     * @return
     */
    // 假设worker全部属性都被传入
    @PostMapping("/save")
    public String saveWorker(@RequestBody String request) {
        Worker worker = JSONObject.parseObject(request, Worker.class);
        List<Double> mark = new ArrayList<>();
        worker.setMark(mark);
        workerDao.saveWorker(worker);
        return "success";
    }

    @PostMapping("/delete")
    public String deleteWorker(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        workerDao.deleteWorker(id);
        return "success";
    }

    @PostMapping("/update/name")
    public String updateName(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        String name = new org.json.JSONObject(request).getString("name");
        workerDao.updateName(id, name);
        return "success";
    }

    @PostMapping("/update/image")
    public String updateImage(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        String image = new org.json.JSONObject(request).getString("image");
        workerDao.updateImage(id, image);
        return "success";
    }

    @PostMapping("/update/mark")
    public String updateMark(@RequestBody String request) {
        String id = new org.json.JSONObject(request).getString("id");
        double mark = Double.parseDouble(new org.json.JSONObject(request).getString("mark"));
        workerDao.updateMark(id, mark);
        return "success";
    }

    @GetMapping("/get/id")
    public Worker getWorkerById(HttpServletRequest request) {
        String id = request.getParameter("id");
        return workerDao.getWorkerById(id);
    }

    @GetMapping("/get")
    public List<Worker> getAllWorker() {
        return workerDao.getAllWorker();
    }

    @PostMapping("/update/phone")
    public String updatePhone(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.parseObject(request);
        String id = jsonObject.getString("id");
        String phone = jsonObject.getString("phone");
        workerDao.updatePhone(id, phone);
        return "success";
    }
}
