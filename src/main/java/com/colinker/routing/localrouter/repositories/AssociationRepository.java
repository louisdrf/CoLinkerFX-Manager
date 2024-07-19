package com.colinker.routing.localrouter.repositories;

import com.colinker.models.Association;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociationRepository extends MongoRepository<Association, String>  {
    @Query("{ 'member.user': ?0, 'member.isBlocked': false }")
    List<Association> findAssociationsByUserid(ObjectId userId);
}
