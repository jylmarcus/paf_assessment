package vttp2023.batch3.assessment.paf.bookings.models;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookingForm implements Serializable{
    private String id;
    private String lid;
    private String name;
    private String email;
    private Date arrival;
    private Integer duration;
}
