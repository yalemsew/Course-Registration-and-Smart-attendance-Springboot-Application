package edu.miu.courseregistrationcore.domain;


import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@ToString
public class Session {
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;


    public Session(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }


}

