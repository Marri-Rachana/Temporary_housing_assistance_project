package com.housebooking.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.FavouritesModel;


@Repository
public interface FavouritesRepo extends JpaRepository<FavouritesModel, Long> {


	@Query( value = "select * from favourites where user_id = :id", nativeQuery = true)
	List<FavouritesModel> findUserFavs(@Param("id") Long id);

}
