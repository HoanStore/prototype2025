CREATE TABLE chat_messages (
                               id IDENTITY PRIMARY KEY,
                               room_id VARCHAR(255) NOT NULL,
                               sender VARCHAR(255) NOT NULL,
                               content TEXT NOT NULL,
                               timestamp VARCHAR(255) NOT NULL
);
