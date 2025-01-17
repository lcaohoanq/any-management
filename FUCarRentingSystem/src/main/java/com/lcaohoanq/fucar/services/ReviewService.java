package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Review;
import com.lcaohoanq.fucar.repositories.IReviewRepository;
import com.lcaohoanq.fucar.repositories.ReviewRepository;
import java.util.List;

public class ReviewService implements IReviewService {

    private final IReviewRepository reviewRepository;

    public ReviewService(String name) {
        reviewRepository = new ReviewRepository(name);
    }

    @Override
    public void save(Review car) {
        reviewRepository.save(car);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        reviewRepository.delete(id);
    }

    @Override
    public void update(Review car) {
        reviewRepository.update(car);
    }

    @Override
    public Review findById(Integer id) {
        return reviewRepository.findById(id);
    }
}
