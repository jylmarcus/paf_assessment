package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.models.SearchForm;
import vttp2023.batch3.assessment.paf.bookings.models.SummarizedListing;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {
	
	@Autowired
	private ListingsRepository listRepo;
	//TODO: Task 2
	public List<String> getListOfCountries(){
		List<String> result = listRepo.getCountries().into(new ArrayList<String>());
		return result;
	}
	
	//TODO: Task 3
	public List<SummarizedListing> getListingsBySearchCriteria(SearchForm searchForm){
		List<Document> result = listRepo.getListingsBySearchCriteria(searchForm.getCountry(), searchForm.getGuests(), searchForm.getMin(), searchForm.getMax());

		List<SummarizedListing> listings = new ArrayList<SummarizedListing>();
		for(Document d : result) {
			SummarizedListing listing = new SummarizedListing(d.getString("_id"), d.getEmbedded(Arrays.asList("address"), Document.class).getString("street"), d.getDouble(listRepo.F_PRICE), d.getEmbedded(Arrays.asList("images"), Document.class).getString("picture_url"), d.getEmbedded(Arrays.asList("address"), Document.class).getString("country"));
			listings.add(listing);
		}
		return listings;
	}

	//TODO: Task 4
	

	//TODO: Task 5


}
