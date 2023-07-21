package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.BookingForm;
import vttp2023.batch3.assessment.paf.bookings.models.FullListing;
import vttp2023.batch3.assessment.paf.bookings.models.SearchForm;
import vttp2023.batch3.assessment.paf.bookings.models.SummarizedListing;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

@Controller
public class ListingsController {

	@Autowired
	private ListingsService listService;
	//TODO: Task 2
	@GetMapping("/index")
	public String getLandingPage(Model model){
		model.addAttribute("countryList", listService.getListOfCountries());
		model.addAttribute("searchForm", new SearchForm());
		return "index";
	}
	
	//TODO: Task 3
	@GetMapping("/search")
	public String searchListings(@Valid @ModelAttribute("searchForm") SearchForm searchForm, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("countryList", listService.getListOfCountries());
			return "index";
		}

		if(!searchForm.checkRange()) {
			bindingResult.addError(new ObjectError("range", "Invalid range"));
			model.addAttribute("countryList", listService.getListOfCountries());
			return "index";
		}
		List<SummarizedListing> listings = listService.getListingsBySearchCriteria(searchForm);

		if(listings.size()==0){
			return "noListings";
		}

		model.addAttribute("searchListings", listings);
		return "searchResult";
	}

	//TODO: Task 4
	@GetMapping("/search/{id}")
	public String getListing(@PathVariable("id") String id, @RequestHeader(value = HttpHeaders.REFERER, required = false) String referrer, Model model){
		FullListing listing = listService.getListingById(id);
		model.addAttribute("listing", listing);
		model.addAttribute("bookingForm", new BookingForm());

		//back link or button to return to view 2
		if(referrer != null) {
			model.addAttribute("prevPage", referrer);
		}

		return "detailedlisting";
	}
	

	//TODO: Task 5
	@PostMapping("/book")
	public String bookAccomodation(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model){
		

		return "bookingSuccess";
	}


}
