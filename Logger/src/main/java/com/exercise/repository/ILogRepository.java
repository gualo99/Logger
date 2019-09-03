package com.exercise.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exercise.model.Log;

@Repository
public interface ILogRepository extends CrudRepository<Log, Long>{

    @Query("select l from Log l where l.message like ?1")
    Log findByMessage(String message);
	
}
