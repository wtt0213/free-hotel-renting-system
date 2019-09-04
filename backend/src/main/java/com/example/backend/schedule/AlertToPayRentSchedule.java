package com.example.backend.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.backend.model.LongRent;
import com.example.backend.model.LongRentDao;

import com.example.backend.model.Tenant;
import com.example.backend.model.TenantDao;
import com.example.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * AlertToPayRentSchedule
 */
@Component
public class AlertToPayRentSchedule {

    @Autowired
    public LongRentDao longRantDao;

    @Autowired
    public TenantDao tenantDao;

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void alertToPayRentSchedule() {
        List<LongRent> longRents = longRantDao.getAllPassedLongRents();
        // 遍历每个长租订单
        for (LongRent longRent : longRents) {
            Date today = new Date();
            Date startDate = longRent.getStartDate();
            Date endDate = longRent.getEndDate();
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTime(today);

            // 判断这个订单是否有效
            if (today.before(endDate) && today.after(startDate)) {

                // 计算当前租房月的应缴费的日期rentCal
                Calendar rentCal = Calendar.getInstance();
                rentCal.setTime(today); // 设置了rentCal的年
                int startDay = startCal.get(Calendar.DATE);
                int rentDay = startDay;
                rentCal.set(Calendar.DATE, rentDay); // 设置了rentCal的天
                int curDay = todayCal.get(Calendar.DATE); // 设置了rentCal的月
                if (curDay > rentDay)
                    rentCal.add(Calendar.MONTH, 1);

                // 计算当前租房月的发邮件的日期emailDate
                Calendar emailCal = Calendar.getInstance();
                emailCal.setTime(rentCal.getTime());
                rentCal.add(Calendar.DATE, -7);
                Date emailDate = emailCal.getTime();

                // 查询这个月有无缴费
                int rentMonth = rentCal.get(Calendar.MONTH);
                int startMonth = startCal.get(Calendar.MONTH);
                int num = rentMonth - startMonth;
                ArrayList<Boolean> payed = longRent.getPayed();
                Boolean isPayed = payed.get(num);

                // 如果没有缴费，而且到了开始提示的日期
                if (isPayed.booleanValue() == false && emailDate.before(today)) {
                    String tenantId = longRent.getTenantId();
                    Tenant tenant = tenantDao.getTenantById(tenantId);
                    String email = tenant.getEmail();

                    String rentString = String.format("%tF", rentCal.getTime());
                    String content = "尊敬的用户：\n\n" + "\t您好！\n\n" + "\t您收到这封邮件是因为您当月尚未在Free租房网站上完成缴费。\n\n\t"
                            + "\t当月缴费日期截止到：" + rentString + "。\n\n" + "\t感谢您的配合！";
                    mailService.sendSimpleMail(email, "Free租房网站提醒缴费邮件", content);
                }
            }
        }
    }
}