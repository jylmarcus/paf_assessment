package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2023.batch3.assessment.paf.bookings.models.BookingForm;
import vttp2023.batch3.assessment.paf.bookings.models.FullListing;
import vttp2023.batch3.assessment.paf.bookings.models.SearchForm;
import vttp2023.batch3.assessment.paf.bookings.models.SummarizedListing;
import vttp2023.batch3.assessment.paf.bookings.repositories.BookingRepository;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {
	
	@Autowired
	private ListingsRepository listRepo;

	@Autowired
	private BookingRepository bookingRepo;
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
			SummarizedListing listing = ListingsUtils.createSummarizedListing(d);
			listings.add(listing);
		}
		return listings;
	}

	//TODO: Task 4
	public FullListing getListingById(String id) {
		Document result = listRepo.getListingById(id);
		SummarizedListing listSumm = ListingsUtils.createSummarizedListing(result);
		FullListing listFull = ListingsUtils.createFullListing(listSumm, result);
		return listFull;
	}
	

	//TODO: Task 5
	public boolean checkVacancyAvailable(String id, Integer duration) {
		Integer vacancy = bookingRepo.getVacancyById(id);

		if(duration > vacancy) {
			return false;
		}
		return true;
	}

	@Transactional
	public String createReservation(BookingForm bookingForm) throws Exception {
		bookingForm.setId(UUID.randomUUID().toString().substring(0, 8));
		Integer reservationInsertResult = bookingRepo.insertReservation(bookingForm.getId(), bookingForm.getName(), bookingForm.getEmail(), bookingForm.getLid(), bookingForm.getArrival(), bookingForm.getDuration());
		if(reservationInsertResult == 0) {
			throw new Exception("Insert failed");
		}

		Integer updateVacancyResult = bookingRepo.updateVacancy(bookingForm.getLid(), bookingForm.getDuration());

		if(updateVacancyResult == 0) {
			throw new Exception("Update failed");
		}

		return bookingForm.getId();
	}


}
