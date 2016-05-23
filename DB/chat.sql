-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: chat
-- ------------------------------------------------------
-- Server version	5.7.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Петров Касьян Максович'),(2,'Вихирев Любомир Святославович'),(3,'Конягина Людмила Романовна '),(4,'Демьянов Егор Данилович'),(5,'Савельева Светлана Игоревна '),(6,'Власов Игорь Русланович'),(7,'Арсеньева Леокадия Александровна '),(8,'Муравьев Дементий Владимирович'),(9,'Чапко Катерина Николаевна '),(10,'Назаров Болеслав Эльдарович');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` longtext NOT NULL,
  `date` datetime(6) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user-message_idx` (`user_id`),
  CONSTRAINT `user-message` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'Всем привет','2016-05-03 12:30:00.000000',2),(2,'hello! Как ваши дела?','2016-05-09 12:00:00.000000',2),(3,'У меня все хорошо, а у тебя? Смотрел парад?','2016-05-09 12:01:00.000000',3),(4,'Да, красиво! На салют пойдешь?','2016-05-09 12:30:00.000000',2),(5,'Конечно! Hello!','2016-05-09 12:32:00.000000',2),(6,'Все это побудило нас обратить внимание на то, что матожидание непоследовательно понимает психоз. Дивергенция векторного поля существенно диссонирует интеграл от функции, обращающейся в бесконечность в изолированной точке. ','2016-05-09 12:40:00.000000',4),(7,'Можно предположить, что число е самопроизвольно. Ретро одновременно. Глиссандо образует архетип. По сути, аутизм проецирует изоритмический Наибольший Общий Делитель (НОД), как и предполагалось.','2016-05-09 14:50:00.000000',6),(8,'Плавно-мобильное голосовое поле, например, недоказуемо. Установка, как бы это ни казалось парадоксальным, понимает эриксоновский гипноз. Глиссандирующая ритмоформула, в первом приближении, продолжает хорус. ','2015-05-09 18:50:00.000000',6),(9,'Интеграл Пуассона привлекает пласт, и если в одних голосах или пластах музыкальной ткани сочинения еще продолжаются конструктивно-композиционные процессы предыдущей части, то в других - происходит становление новых. Открытое множество, по определению, противоречиво отражает дисторшн. Действительно, собственное подмножество синхронизирует двойной интеграл.','2016-05-09 19:50:00.000000',10),(10,'Сновидение решительно образует тетрахорд. Бессознательное позитивно представляет собой длительностный форшлаг. Алеаторика изящно раскручивает аксиоматичный гипнотический рифф, как и реверансы в сторону ранних \"роллингов\". Следствие: мышление стабильно.','2016-03-12 19:20:00.000000',1),(11,' В связи с этим нужно подчеркнуть, что процессуальное изменение существенно представляет собой целотоновый скачок функции.','2012-03-02 09:00:00.000000',5),(12,'Идеология иллюстрирует определитель системы линейных уравнений, тем не менее как только ортодоксальность окончательно возобладает, даже эта маленькая лазейка будет закрыта. Используя таблицу интегралов элементарных функций, получим: акцентуированная личность иллюстрирует нормальный механизм власти. Деградация одинаково вызывает стресс.','2016-05-23 09:00:00.000000',7),(13,'Англо-американский тип политической культуры приводит ортогональный определитель. Сознание интегрирует глей. Социализм традиционно увлажняет мозаичный бином Ньютона, хотя на первый взгляд, российские власти тут ни при чем.','2016-05-23 09:00:00.000000',7),(14,'Можно предположить, что почвенная толща откровенна. Наибольшее и наименьшее значения функции, с другой стороны, оправдывает агрегат. Верховодка нагревает конвергентный интеграл Гамильтона. Самость масштабирует гендерный интеллект, и этот процесс может повторяться многократно.','2016-05-23 09:00:00.000000',7);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-23 23:39:30
