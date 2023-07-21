package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
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
	public String searchListings(@Valid @ModelAttribute("searchForm") SearchForm searchForm, BindingResult bindingResult, HttpSession session, Model model) {
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

		session.setAttribute("searchForm", searchForm);
		model.addAttribute("searchListings", listings);
		return "searchResult";
	}

	//TODO: Task 4
	@GetMapping("/search/{id}")
	public String getListing(@PathVariable("id") String id, HttpSession session, Model model){
		FullListing listing = listService.getListingById(id);
		model.addAttribute("listing", listing);
		model.addAttribute("listingId", id);
		model.addAttribute("bookingForm", new BookingForm());
		model.addAttribute("previousSearch", session.getAttribute("searchForm"));

		session.setAttribute("listing", listing);

		return "detailedlisting";
	}
	

	//TODO: Task 5
	@PostMapping("/book/{lid}")
	public String bookAccomodation(@ModelAttribute("bookingForm") BookingForm bookingForm, @PathVariable("lid") String lid, HttpSession session, Model model, BindingResult bindingResult) throws Exception{
		//System.out.println(">>>listing id: " + lid);
		if(!listService.checkVacancyAvailable(lid, bookingForm.getDuration())){
			bindingResult.addError(new ObjectError("range", "Invalid range"));
			model.addAttribute("listing", session.getAttribute("listing"));

			return "detailedlisting";
		}

		bookingForm.setLid(lid);
		String bookingId = listService.createReservation(bookingForm);
		model.addAttribute("bookingId", bookingId);

		return "bookingSuccess";
	}


}
