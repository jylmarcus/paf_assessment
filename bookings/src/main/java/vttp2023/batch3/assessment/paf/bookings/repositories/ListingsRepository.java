package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import com.mongodb.client.DistinctIterable;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public final String C_NAME = "listings";
	public final String F_COUNTRY = "address.country";
	public final String F_ACCOMMODATION = "accommodates";
	public final String F_PRICE = "price";
	public final String F_STREET = "address.street";
	public final String F_IMAGE = "images.picture_url";

	// TODO: Task 2
	public DistinctIterable<String> getCountries() {
		// db.listings.distinct('address.country');
		DistinctIterable<String> result = mongoTemplate.getCollection(C_NAME).distinct(F_COUNTRY, String.class);
		return result;
	}

	// TODO: Task 3
	public List<Document> getListingsBySearchCriteria(String country, Integer guests, Integer min, Integer max) {
		TextCriteria countryCriteria = TextCriteria.forDefaultLanguage().matching(country);
		Criteria guestCriteria = Criteria.where(F_ACCOMMODATION).is(guests);
		Criteria rangeCriteria = Criteria.where(F_PRICE).gte(min).lte(max);

		/*
		 * db.listings.createIndex({'address.country' : "text"});
		 * 
		 * db.listings.find({
		 * $text: {$search: country},
		 * 'accommodates' : guests,
		 * 'price' : {$gte: min, $lte: max}
		 * },
		 * {
		 * 'address.street': 1,
		 * price: 1,
		 * 'images.picture_url': 1,
		 * 'address.country': 1
		 * }
		 * ).sort({price: -1})
		 */

		Query query = TextQuery.queryText(countryCriteria).addCriteria(guestCriteria).addCriteria(rangeCriteria);
		query.fields().include(F_STREET, F_PRICE, F_IMAGE, F_COUNTRY);
		query.with(Sort.by(Sort.Direction.DESC, F_PRICE));

		List<Document> results = mongoTemplate.find(query, Document.class, C_NAME);
		return results;
	}

	// TODO: Task 4
	public void getListingById(String id) {
		
	}

	// TODO: Task 5

}
