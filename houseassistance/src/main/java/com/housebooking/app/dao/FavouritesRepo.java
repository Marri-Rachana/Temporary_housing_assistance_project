package com.housebooking.app.dao;

import com.housebooking.app.model.FavouritesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FavouritesRepo extends JpaRepository<FavouritesModel, Long> {

}
