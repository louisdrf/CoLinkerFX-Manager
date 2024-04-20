package com.colinker.routes;

import io.javalin.Javalin;
import com.colinker.database.LocalDatabase;

public class LocalRouter {

    static Javalin app;
    public static void initialize() {
        app = Javalin.create()
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070);
    }
    private final String baseUrl = LocalDatabase.getConnexionString();

   /* public static void login(String email, String password) {
        app.post("/login", ctx -> {
            UserRepository.findByLogin(login, password);
            if (user != null) {
                ctx.json(user);
            } else {
                ctx.status(401).result("Utilisateur non trouvé ou mot de passe incorrect");
            }
        });
    }*/
}
