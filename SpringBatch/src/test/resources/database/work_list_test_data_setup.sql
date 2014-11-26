DROP TABLE IF EXISTS `work_list`;

CREATE TABLE `work_list` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `work_name` VARCHAR(45) NULL,
  `processed_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

INSERT INTO `work_list` (`work_name`) VALUES ("Odessa"),("Kendall"),("Constance"),("Clayton"),("Elton"),("Stone"),("Julie"),("Russell"),("Maite"),("Randall"),("Eagan"),("Cruz"),("Azalia"),("Carla"),("Vivian"),("Macey"),("Odysseus"),("Kenneth"),("Renee"),("Zane"),("Kieran"),("Donovan"),("Kirby"),("Cleo"),("Quamar"),("Beatrice"),("Giacomo"),("Keefe"),("Rose"),("Merritt"),("Cecilia"),("Rahim"),("Germane"),("Rahim"),("Moana"),("Dakota"),("Buffy"),("Jessamine"),("Nicole"),("Jerry"),("Xena"),("Victoria"),("Ella"),("Libby"),("Laurel"),("Samuel"),("Camilla"),("Camilla"),("Damian"),("Marcia"),("Hedley"),("Emi"),("Grady"),("Wang"),("Jolie"),("Callie"),("Idola"),("Jonah"),("Seth"),("Craig"),("Demetrius"),("Nayda"),("Ifeoma"),("Chelsea"),("Evangeline"),("Skyler"),("Ramona"),("Uma"),("Ali"),("Quincy"),("Walker"),("Nathaniel"),("Noel"),("Chadwick"),("Hayfa"),("Yoshi"),("Damian"),("Priscilla"),("Kameko"),("Daria"),("Pascale"),("Geraldine"),("Gretchen"),("Ebony"),("Hedy"),("Jana"),("Tucker"),("Louis"),("Nichole"),("Kaseem"),("Talon"),("Alexa"),("Dante"),("Colin"),("Hermione"),("Velma"),("Abdul"),("Burke"),("Branden"),("Callum");
