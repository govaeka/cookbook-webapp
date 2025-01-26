-- MariaDB initialization script for schema "cookbook"
-- run this after structure definition
-- best import using client command "source <path to this file>"

SET CHARACTER SET utf8mb4;
USE cookbook;
DELETE FROM AbstractEntity WHERE identity <> 1;


-- insertion of some people
INSERT INTO AbstractEntity VALUES (0, "Person", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @p01 = LAST_INSERT_ID();
INSERT INTO Person VALUES (@p01, 1, "guest@cookbook.de", SHA2("guest",256), "USER", NULL, "Guest", "Any", "10557", "Spreeweg 1", "Berlin", "Deutschland");

INSERT INTO AbstractEntity VALUES (0, "Person", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @p02 = LAST_INSERT_ID();
INSERT INTO Person VALUES (@p02, 1, "ines.bergmann@web.de", SHA2("ines",256), "ADMIN", "Doctor", "Bergmann", "Ines", "10999", "Wiener Strasse 42", "Berlin", "Deutschland");

INSERT INTO AbstractEntity VALUES (0, "Person", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @p03 = LAST_INSERT_ID();
INSERT INTO Person VALUES (@p03, 1, "sascha.baumeister@gmail.com", SHA2("sascha",256), "ADMIN", NULL, "Baumeister", "Sascha", "10243", "Hildegard-Jadamowitz-Strasse 22", "Berlin", "Deutschland");


-- insertion of some victuals
INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v01 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v01, 1, NULL, "VEGAN", "Wasser", "Diwasserstoffoxid (H2O)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v02 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v02, 1, NULL, "VEGAN", "Kochsalz", "Natriumchlorid (NaCl)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v03 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v03, 1, NULL, "VEGAN", "Pfeffer (Pulver)", "Gemahlener Pfeffer");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v04 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v04, 1, NULL, "VEGAN", "Pfeffer (Schrot)", "Geschroteter Pfeffer");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v05 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v05, 1, NULL, "VEGAN", "Pfeffer (Koerner)", "Gekoernter Pfeffer");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v06 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v06, 1, NULL, "VEGAN", "Chili (Pulver)", "Chilipulver");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v07 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v07, 1, NULL, "VEGAN", "Paprika (Pulver)", "Paprikapulver");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v08 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v08, 1, NULL, "VEGAN", "Curry (Pulver)", "Currypulver aus Kurkuma, Chili, Koriander, Kreuzkuemmel, Bockshornklee, Senfkoernern und schwarzem Pfeffer");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v09 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v09, 1, @p01, "VEGAN", "Muskat (Nuss)", "Muskatnuss");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v10 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v10, 1, @p01, "VEGAN", "Muskat (Pulver)", "Muskatpulver");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v11 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v11, 1, @p02, "VEGAN", "Oregano (getrocknet)", "Oreganoblaetter (trocken)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v12 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v12, 1, @p02, "VEGAN", "Oregano (frisch)", "Oreganoblaetter (frisch)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v13 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v13, 1, @p03, "LACTO_OVO_VEGETARIAN", "Ei", "Huehnerei");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v14 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v14, 1, @p03, "CARNIVORIAN", "Hackfleisch (gemischt)", "Rinder- und Schweinehackfleisch gemischt");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v15 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v15, 1, @p03, "CARNIVORIAN", "Hackfleisch (Rind)", "Rinderhackfleisch");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v16 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v16, 1, @p03, "CARNIVORIAN", "Hackfleisch (Schwein)", "Schweinehackfleisch");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v17 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v17, 1, @p03, "VEGAN", "Pasta (Spaghetti)", "Spaghetti-Nudel");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v18 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v18, 1, @p03, "VEGAN", "Pasta (Linguine)", "Schmalband-Nudel (Linguine)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v19 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v19, 1, @p03, "VEGAN", "Pasta (Band)", "Bandnudel (Fettuccine, Pappardine, ...)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v20 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v20, 1, @p03, "VEGAN", "Pasta (Rigatoni)", "Roehren-Nudel (Rigatoni)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v21 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v21, 1, @p03, "VEGAN", "Pasta (Penne)", "Roehren-Nudel (Penne)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v22 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v22, 1, @p03, "LACTO_OVO_VEGETARIAN", "Pasta (Horn)", "Hoernchen-Nudel");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v23 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v23, 1, @p03, "VEGAN", "Pasta (Spirale)", "Spiral-Nudel (Spirelli)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v24 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v24, 1, @p03, "VEGAN", "Pasta (Farfale)", "Schmetterlings-Nudel (Farfale)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v25 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v25, 1, @p03, "LACTO_VEGETARIAN", "Pasta (Tortellini)", "Nudel-Tasche (Tortellini)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v26 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v26, 1, @p03, "LACTO_OVO_VEGETARIAN", "Pasta (Spaetzle geschabt)", "Schwaebische geschabte Spaetzle");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v27 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v27, 1, @p03, "LACTO_OVO_VEGETARIAN", "Pasta (Spaetzle gepresst)", "Schwaebische gepresste Spaetzle");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v28 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v28, 1, @p03, "LACTO_OVO_VEGETARIAN", "Pasta (Spaetzle gehobelt)", "Schwaebische gehobelte Spaetzle");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v29 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v29, 1, @p03, "CARNIVORIAN", "Pasta (Maultasche)", "Schwaebische Maultasche (Maulschelle, Laubfrosch)");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v30 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v30, 1, @p02, "VEGAN", "Tomate (Passiert)", "Passierte Tomate");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v31 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v31, 1, @p02, "VEGAN", "Tomatenmark (2-fach)", "2-fach  konzentriertes Tomatenmark");

INSERT INTO AbstractEntity VALUES (0, "Victual", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @v32 = LAST_INSERT_ID();
INSERT INTO Victual VALUES (@v32, 1, @p02, "VEGAN", "Tomatenmark (3-fach)", "3-fach  konzentriertes Tomatenmark");


-- insertion of some recipes
INSERT INTO AbstractEntity VALUES (0, "Recipe", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @r01 = LAST_INSERT_ID();
INSERT INTO Recipe VALUES (@r01, 1, NULL, "MAIN_COURSE", "Spaghetti Bolognese", "Spaghetti mit Hackfleischsosse", "Spaghetti ca. 8-10min in Salzwasser kochen bis sie al-dente sind. Hackfleisch zusammen mit den gehackten Zwiebeln anbraten, Gewuerze, Tomatenmark sowie passierte Tomaten zugeben, alles gut vermengen, danach abschmecken.");


-- insertion of some ingredients
INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i01 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i01, @r01, @v01, 3.0, "LITRE");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i02 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i02, @r01, @v17, 250, "GRAM");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i03 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i03, @r01, @v14, 250, "GRAM");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i04 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i04, @r01, @v30, 0.5, "LITRE");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i05 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i05, @r01, @v32, 250, "GRAM");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i06 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i06, @r01, @v02, 1, "TABLESPOON");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i07 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i07, @r01, @v03, 1, "TEASPOON");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i08 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i08, @r01, @v06, 1, "TEASPOON");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i09 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i09, @r01, @v07, 2, "TEASPOON");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i10 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i10, @r01, @v10, 2, "PINCH");

INSERT INTO AbstractEntity VALUES (0, "Ingredient", 1, UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);
SET @i11 = LAST_INSERT_ID();
INSERT INTO Ingredient VALUES (@i11, @r01, @v11, 1, "TABLESPOON");


-- display content of table AbstractEntity
SELECT * from AbstractEntity;
