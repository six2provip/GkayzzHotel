package com.ps20652.Hotel.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "work_schedules")
@Data
public class WorkSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // Getters and setters
}
