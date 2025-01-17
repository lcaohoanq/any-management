package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Review;
import java.util.List;

public interface IReviewDAO {
    void save(Review review);
    List<Review> findAll();
    void delete(Integer id);
    void update(Review review);
    Review findById(Integer id);
}
