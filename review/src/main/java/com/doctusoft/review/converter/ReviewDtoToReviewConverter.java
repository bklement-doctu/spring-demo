package com.doctusoft.review.converter;

import org.springframework.core.convert.converter.Converter;

import com.doctusoft.review.Review;
import com.doctusoft.review.ReviewDto;

public class ReviewDtoToReviewConverter implements Converter<ReviewDto, Review> {

	@Override
	public Review convert(ReviewDto dto) {
		Review entity = new Review();
		entity.setBookId(dto.getBookId());
		entity.setText(dto.getText());
		return entity;
	}

}
