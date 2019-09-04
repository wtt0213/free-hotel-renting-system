package com.example.backend.model;

import java.math.BigDecimal;
import java.util.List;

public interface RoomDao {

    /**
     * 返回数据库中所有的房间信息
     * @return
     */
    List<Room> getAllRooms();

    /**
     * 根据短租日租价格区间，返回房子的列表
     * @param up 价格上限
     * @param down 价格下限
     * @return 价格在区间内的房子的列表
     */
    List<Room> getRoomsByShortPriceRange(BigDecimal up, BigDecimal down);

    /**
     * 根据长租月租价格区间，返回房子的列表
     * @param up 价格上限
     * @param down 价格下限
     * @return 价格在区间内的房子的列表
     */
    List<Room> getRoomsByLongPriceRange(BigDecimal up, BigDecimal down);

    /**
     * 根据位置信息，获取房子的列表
     * @param location
     * @return
     */
    List<Room> getRoomsByLocation(String location);

    /**
     * 根据城市信息，获取房子的列表
     * @param city
     * @return
     */
    List<Room> getRoomsByCity(String city);

    /**
     * 根据房间的类型，获取房子列表
     * @param type
     * @return
     */
    List<Room> getRoomsByType(int type);

    /**
     * 根据这一系列信息返回符合查询条件的短租日租房子的列表
     * @param up
     * @param down
     * @param city
     * @param type
     * @return
     */
    List<Room> getRoomsByTypeShortPriceRangeCity(BigDecimal up, BigDecimal down, String city, int type);


    /**
     * 根据这一系列信息返回符合查询条件的长租月租房子的列表
     * @param up
     * @param down
     * @param city
     * @param type
     * @return
     */
    List<Room> getRoomsByTypeLongPriceRangeCity(BigDecimal up, BigDecimal down, String city, int type);

    /**
     * 在数据库中新插入一条Room记录
     * @param room
     */
    void saveRoom(Room room);

    /**
     * 更新数据库中的一条Room记录
     * @param room
     */
    void updateRoom(Room room);

    /**
     * 根据id删除一个房间数据记录
     * @param id
     */
    void deleteRoomById(String id);

    Room getRoomById(String id);

    /**
     * 检查房子现在是否可用，包括是否已经被申请
     * @param id
     * @return
     */
    boolean isAvailable(String id);

    /**
     * 把房间的可用性更新成available所指定的那样
     * @param id
     * @param available
     */
    void updateAvailable(String id, boolean available);

}
