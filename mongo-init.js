db.createUser({
    user: "${MONGO_USERNAME}",
    pwd: "${MONGO_PASSWORD}",
    roles: [
        {
            role: "readWrite",
            db: "${MONGO_DATABASE}"
        }
    ]
});

db.createCollection("testCollection");