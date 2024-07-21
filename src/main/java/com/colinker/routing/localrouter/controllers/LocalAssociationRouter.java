package com.colinker.routing.localrouter.controllers;

import com.colinker.models.Association;
import com.colinker.models.Room;
import com.colinker.routing.localrouter.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LocalAssociationRouter {
    private static AssociationService associationService;

    @Autowired
    public LocalAssociationRouter(AssociationService associationService) {
        LocalAssociationRouter.associationService = associationService;
    }

    public static List<Association> getUserAssociations(String username) {
        try {
            return associationService.getUserAssociations(username);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<String> getAssociationMembersName(String associationId) {
        try {
            return associationService.getAssociationMembers(associationId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
