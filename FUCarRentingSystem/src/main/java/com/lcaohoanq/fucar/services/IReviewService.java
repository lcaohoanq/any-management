package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Review;
import java.util.List;

public interface IReviewService {
    void save(Review car);
    List<Review> findAll();
    void delete(Integer id);
    void update(Review car);
    Review findById(Integer id);
}
