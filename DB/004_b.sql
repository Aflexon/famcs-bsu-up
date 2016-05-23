# case sensitive
SELECT * FROM chat.messages WHERE user_id = 2 AND BINARY text LIKE '%hello%';