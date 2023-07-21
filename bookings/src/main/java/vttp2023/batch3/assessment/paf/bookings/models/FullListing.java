package vttp2023.batch3.assessment.paf.bookings.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullListing {
    private SummarizedListing summary;
    private String desc;
    private String suburb;
    private String amenities;
}
