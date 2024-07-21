package com.colinker.routing.remoterouter;

import com.colinker.models.Association;
import com.colinker.services.AssociationService;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RemoteAssociationRouter {

    public static List<Association> getUserAssociations(String username) {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get("/association/userAssociation/username/" + username);
        jsonArray = bodyResponse.getArray();

        if(jsonArray.getJSONObject(0).isEmpty()) return new ArrayList<>();

        return AssociationService.transformAssociationArrayIntoList(jsonArray);
    }
    public static List<String> getAssociationMembersName(String associationId) {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get("/association/associationMembers/" + associationId);
        jsonArray = bodyResponse.getArray();

        if(jsonArray.getJSONObject(0).isEmpty()) return new ArrayList<>();

        return AssociationService.transformAssociationMembersArrayIntoList(jsonArray);
    }
}
