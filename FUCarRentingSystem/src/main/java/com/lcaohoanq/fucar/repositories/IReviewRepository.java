package com.lcaohoanq.fucar.repositories;

import com.lcaohoanq.fucar.models.CarProducer;
import com.lcaohoanq.fucar.models.Review;
import java.util.List;

public interface IReviewRepository {
    void save(Review car);
    List<Review> findAll();
    void delete(Integer id);
    void update(Review car);
    Review findById(Integer id);
}
