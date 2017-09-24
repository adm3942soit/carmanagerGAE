package com.adonis.data.service;

import com.adonis.data.renta.RentaHistory;
import com.adonis.utils.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by oksdud on 29.03.2017.
 */
@Component
public class RentaHistoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RentaHistory> findAll() {
        String sql = "SELECT * FROM renta_history";
        List<RentaHistory> histories = jdbcTemplate.query(sql, new BeanPropertyRowMapper(RentaHistory.class));
        return histories;
    }

    public List<String> findAllWorking() {
        String sql = "SELECT DISTINCT r.VEHICLE FROM renta_history r WHERE NOW() BETWEEN r.FROM_DATE AND r.TO_DATE";
        List<String> vehiclesIds = jdbcTemplate.query(sql, new BeanPropertyRowMapper(RentaHistory.class));
        return vehiclesIds;
    }

    public Integer findCountTripsByDate(Date dateTrip) {
        List<RentaHistory> foundHistories = Lists.newArrayList();
        List<RentaHistory> histories = findAll();
        histories.forEach(rentaHistory -> {
                    if (DateUtils.between(rentaHistory.getFromDate(), rentaHistory.getToDate(), dateTrip )) {
                        foundHistories.add(rentaHistory);
                    }
                }
        );

        return foundHistories.size();
    }

    public RentaHistory findById(Long id) {
        if (id == null) return null;
        String sql = "SELECT * FROM renta_history WHERE ID = ?";

        RentaHistory vehicle = (RentaHistory) jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new BeanPropertyRowMapper(RentaHistory.class));

        return vehicle;
    }

    public RentaHistory findLast() {
        String sql = "SELECT * FROM renta_history ORDER BY ID DESC LIMIT 1";

        RentaHistory vehicle = null;
        try {
            vehicle = (RentaHistory) jdbcTemplate.queryForObject(
                    sql, new Object[]{}, new BeanPropertyRowMapper(RentaHistory.class));
        } catch (Exception e) {
            return null;
        }

        return vehicle;
    }
    public Date getAvailableDate(String vehicleNumber){
      return getHistory(vehicleNumber).getToDate();
    }
    public RentaHistory getHistory(String vehicleNumber){
        String sql = "SELECT * FROM renta_history where vehicle = "+vehicleNumber+" ORDER BY ID DESC LIMIT 1";
        RentaHistory rentaHistory = null;
        try {
            rentaHistory = (RentaHistory) jdbcTemplate.queryForObject(
                    sql, new Object[]{}, new BeanPropertyRowMapper(RentaHistory.class));
        } catch (Exception e) {
            return null;
        }

        return rentaHistory;
    }

    public int findTotalRentaHistory() {

        String sql = "SELECT COUNT(*) FROM renta_history";

        int total = jdbcTemplate.queryForObject(sql, Integer.class);

        return total;
    }

    public RentaHistory insert(RentaHistory vehicle) {
        if (vehicle == null) return null;
        try {
            jdbcTemplate.update(
                    "INSERT INTO renta_history " +
                            "(PERSON, VEHICLE, FROM_DATE, TO_DATE, PAID, PRICE, PRICE_DAY, PRICE_WEEK, PRICE_MONTH,SUMMA, UPDATED, CREATED) VALUES " +
                            "(?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{
                            vehicle.getPerson(),
                            vehicle.getVehicle(),
                            vehicle.getFromDate(),
                            vehicle.getToDate(),
                            vehicle.getPaid(),
                            vehicle.getPrice(),
                            vehicle.getPriceDay(),
                            vehicle.getPriceWeek(),
                            vehicle.getPriceMonth(),
                            vehicle.getSumma(),
                            new Date(), new Date()
                    });
        } catch (Exception e) {
            return null;
        }
        return findLast();

    }


    public RentaHistory save(RentaHistory vehicle) {
        if (vehicle == null) return null;
        try {
            jdbcTemplate.update(
                    "UPDATE renta_history SET " +
                            "PERSON=?, " +
                            "VEHICLE=?, " +
                            "PRICE=?, " +
                            "PRICE_DAY=?, " +
                            "PRICE_WEEK=?, " +
                            "PRICE_MONTH=?, " +
                            "SUMMA=?, " +
                            "FROM_DATE=?, " +
                            "TO_DATE=?, " +
                            "PAID=?, " +
                            "UPDATED=? " +
                            "WHERE ID=?",
                    vehicle.getPerson(),
                    vehicle.getVehicle(),
                    vehicle.getPrice(),
                    vehicle.getPriceDay(),
                    vehicle.getPriceWeek(),
                    vehicle.getPriceMonth(),
                    vehicle.getSumma(),
                    vehicle.getFromDate(),
                    vehicle.getToDate(),
                    vehicle.getPaid(),
                    new Date(),
                    vehicle.getId());
        } catch (Exception e) {
            return null;
        }
        return findById(vehicle.getId());
    }


    public void delete(RentaHistory vehicle) {
        if (vehicle == null) return;
        jdbcTemplate.execute("DELETE FROM renta_history WHERE ID=" + vehicle.getId());
    }

}
