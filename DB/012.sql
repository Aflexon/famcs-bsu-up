SELECT users.name, COUNT(*) AS count FROM messages JOIN users ON users.id = messages.user_id WHERE DATE(date) = '2016-05-09' GROUP BY user_id 