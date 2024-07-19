package com.colinker.routing.localrouter.services;

import com.colinker.models.Association;
import com.colinker.models.Member;
import com.colinker.models.User;
import com.colinker.routing.localrouter.repositories.AssociationRepository;
import com.colinker.routing.localrouter.repositories.UserRepository;
import com.colinker.services.UserPropertiesService;
import com.colinker.views.ApiResponseModal;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssociationService {

    private final AssociationRepository associationRepository;
    private final UserService userService;

    @Autowired
    public AssociationService(AssociationRepository associationRepository, UserService userService) {
        this.associationRepository = associationRepository;
        this.userService = userService;
    }

    public List<Association> getUserAssociations(String username) {
        String userId = userService.getUserIdByName(username);
        if (userId == null) {
            System.out.println("aucun user trouvé pour : " + username);
            return new ArrayList<>();
        }
        ObjectId objectId = new ObjectId(userId);
        return associationRepository.findAssociationsByUserid(objectId);
    }

    public List<String> getAssociationMembers(String associationId) {
        Association association = associationRepository.findById(associationId).orElse(null);
        if (association == null) {
            ApiResponseModal.showErrorModal("Aucune association n'a pu être récupérée");
            return new ArrayList<>();
        }
        List<String> membersName = new ArrayList<>();
        for(Member member : association.getMember()) {
            Optional<User> user = userService.getUserById(String.valueOf(member.getUser()));
            if(user.isPresent()) {
                if(Objects.equals(user.get().getUsername(), UserPropertiesService.getUsername())) continue;
                membersName.add(user.get().getUsername());
            }
        }

        return membersName;
    }
}
