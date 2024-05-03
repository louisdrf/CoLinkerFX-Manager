package com.colinker.repositories;

import com.colinker.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Vous pouvez ajouter d'autres méthodes de requête personnalisées ici
}