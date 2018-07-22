package com.doctusoft.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

	Page<Review> findAllByBookId(Pageable pageable, String bookId);
	
	void deleteAllByBookId(String bookId);
	
}
