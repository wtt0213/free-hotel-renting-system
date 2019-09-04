package com.example.backend.model;

import java.util.List;

public interface WorkerDao {

    void saveWorker(Worker worker);

    void deleteWorker(String id);

    void updateName(String id, String name);

    void updateImage(String id, String image);

    void updateMark(String id, double mark);

    void updatePhone(String id, String phone);

    Worker getWorkerById(String id);

    Worker getWorkerByName(String name);

    List<Worker> getAllWorker();
}
