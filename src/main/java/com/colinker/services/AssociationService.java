package com.colinker.services;

import com.colinker.models.Association;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssociationService {

    public static List<Association> transformAssociationArrayIntoList(JSONArray associationObjectsArray) {
        if (associationObjectsArray.isEmpty()) return List.of();

        List<Association> allAssociations = new ArrayList<>();
        for (Object obj : associationObjectsArray) {
            JSONObject associationJson = (JSONObject) obj;
            Association association = new Association
                    (
                            associationJson.getString("_id"),
                            associationJson.getString("name"),
                            transformAssociationMembersArrayIntoList(associationJson.getJSONArray("member")),
                            new ArrayList<>()
                    );

            allAssociations.add(association);
        }
        return allAssociations;
    }

    public static List<String> transformAssociationMembersArrayIntoList(JSONArray associationMembers) {
        if (associationMembers.isEmpty()) return List.of();

        List<String> associationMembersName = new ArrayList<>();

        for (Object member : associationMembers) {
            JSONObject jsonMember = (JSONObject) member;
            JSONObject memberLinkedUser = jsonMember.getJSONObject("user");
            String memberName = memberLinkedUser.getString("username");

            if(Objects.equals(memberName, UserPropertiesService.getUsername())) continue;

            associationMembersName.add(memberName);
        }

        return associationMembersName;
    }
}
