package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.Arrays;

import org.bson.Document;

import vttp2023.batch3.assessment.paf.bookings.models.FullListing;
import vttp2023.batch3.assessment.paf.bookings.models.SummarizedListing;

public class ListingsUtils {

    public static SummarizedListing createSummarizedListing(Document d){
        return new SummarizedListing(d.getString("_id"), d.getEmbedded(Arrays.asList("address"), Document.class).getString("street"), d.getDouble("price"), d.getEmbedded(Arrays.asList("images"), Document.class).getString("picture_url"), d.getEmbedded(Arrays.asList("address"), Document.class).getString("country"));
    }

    public static FullListing createFullListing(SummarizedListing listsumm, Document d){
        return new FullListing(listsumm, d.getString("description"), d.getEmbedded(Arrays.asList("address"), Document.class).getString("suburb"), d.getList("amenities", String.class));
    }
}
