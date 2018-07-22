package com.doctusoft.review.converter;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.core.convert.converter.Converter;

import com.doctusoft.review.Review;
import com.doctusoft.review.ReviewController;
import com.doctusoft.review.ReviewResource;

public class ReviewToReviewResourceConverter implements Converter<Review, ReviewResource> {

	@Override
	public ReviewResource convert(Review entity) {
		ReviewResource resource = new ReviewResource();
		resource.setBookId(entity.getBookId());
		resource.setText(entity.getText());
		resource.add(linkTo(methodOn(ReviewController.class).get(entity.getId())).withSelfRel());
		return resource;
	}

}
