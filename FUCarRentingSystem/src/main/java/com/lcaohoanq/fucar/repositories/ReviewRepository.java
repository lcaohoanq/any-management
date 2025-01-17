package com.lcaohoanq.fucar.repositories;

import com.lcaohoanq.fucar.daos.IReviewDAO;
import com.lcaohoanq.fucar.daos.ReviewDAO;
import com.lcaohoanq.fucar.models.Review;
import java.util.List;

public class ReviewRepository implements IReviewRepository {

    private IReviewDAO reviewDAO;

    public ReviewRepository(String jpaName) {
        reviewDAO = new ReviewDAO(jpaName);
    }

    @Override
    public void save(Review review) {
        reviewDAO.save(review);
    }

    @Override
    public Review findById(Integer id) {
        return reviewDAO.findById(id);
    }

    @Override
    public List<Review> findAll() {
        return reviewDAO.findAll();
    }

    @Override
    public void delete(Integer id) {
        reviewDAO.delete(id);
    }

    @Override
    public void update(Review review) {
        reviewDAO.update(review);
    }
}
