--Users.....................................................
INSERT INTO users (username, password, enabled, apikey, firstname, lastname, emailadress)
    VALUES ('user@mail.com', '$2y$10$yq8BZ6yIcauc1NHKekI4Iu/cgF1GtFlzLLYvtssS0C3fouThfew32', true, '12345678', 'user', 'user', 'user@mail.com');
INSERT INTO authorities (username, authority)
    VALUES ('user@mail.com', 'ROLE_USER');
INSERT INTO users (username, password, enabled, apikey, firstname, lastname, emailadress)
    VALUES ('admin@mail.com', '$2y$10$yq8BZ6yIcauc1NHKekI4Iu/cgF1GtFlzLLYvtssS0C3fouThfew32', true, '12345678', 'admin', 'admin', 'admin@mail.com');
INSERT INTO authorities (username, authority)
    VALUES ('admin@mail.com', 'ROLE_ADMIN');
INSERT INTO users (username, password, enabled, apikey, firstname, lastname, emailadress)
    VALUES ('e.vanduikeren@gmail.com', '$2y$10$yq8BZ6yIcauc1NHKekI4Iu/cgF1GtFlzLLYvtssS0C3fouThfew32', true, '12345678', 'Ellen', 'Van Duikeren', 'e.vanduikeren@gmail.com');
INSERT INTO authorities (username, authority)
    VALUES ('e.vanduikeren@gmail.com', 'ROLE_ADMIN');

--Recipes
--Bramenjam.................................................
INSERT INTO utensils (utensil) VALUES ('schone uitgekookte potjes');

INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (500, 'gram', 'bramen');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (200, 'gram', 'geleisuiker speciaal');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'eetlepel', 'citroensap');

INSERT INTO instructions(instruction) VALUES ('Was de bramen en pureer deze. Dit kan je bijvoorbeeld met een staafmixer doen. Je kan de puree ook nog zeven, als je geen pitjes in je jam wilt.');
INSERT INTO instructions(instruction) VALUES ('Doe de bramenpuree met suiker en citroensap in een pan. Breng het mengsel aan de kook. Blijf voortdurend roeren. Als het kookt, laat je het ongeveer 2 minuten doorkoken. Blijf roeren.');
INSERT INTO instructions(instruction) VALUES ('Giet de bramenjam in de schone potjes. Draai de deksels op de potjes en zet ze op hun kop voor in ieder geval 5 minuten. Daarna mag je ze weer omdraaien. Nu zijn ze vacuüm gezogen.');

INSERT INTO recipes (title, sub_title, persons, source, story, prep_time, cook_time) VALUES ('Bramenjam', 'Voor ca. 600 ml bramenjam', 4, 'https://www.laurasbakery.nl/zelf-bramenjam-maken/',  'Dit is een heel makkelijk recept en bramen zijn overal te vinden. Ook juist op de ruige plekken.', '2 minuten', '10 minuten');

UPDATE utensils SET recipe_id = 1 WHERE id = 1;

UPDATE ingredients SET recipe_id = 1 WHERE id = 1;
UPDATE ingredients SET recipe_id = 1 WHERE id = 2;
UPDATE ingredients SET recipe_id = 1 WHERE id = 3;

UPDATE instructions SET recipe_id = 1 WHERE id = 1;
UPDATE instructions SET recipe_id = 1 WHERE id = 2;
UPDATE instructions SET recipe_id = 1 WHERE id = 3;

INSERT INTO recipe_months(recipe_id, months) VALUES (1, 'JULI');
INSERT INTO recipe_months(recipe_id, months) VALUES (1, 'AUGUSTUS');
INSERT INTO recipe_months(recipe_id, months) VALUES (1, 'SEPTEMBER');
INSERT INTO recipe_months(recipe_id, months) VALUES (1, 'OKTOBER');

INSERT INTO recipe_tags(recipe_id, tags) VALUES (1, 'VEGETARISCH');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (1, 'LACTOSEVRIJ');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (1, 'GLUTENVRIJ');


--Pompoen......................................................
INSERT INTO utensils (utensil) VALUES ('vuurplek');
INSERT INTO utensils (utensil) VALUES ('hout (eik, beuk)');
INSERT INTO utensils (utensil) VALUES ('kernthermometer');
INSERT INTO utensils (utensil) VALUES ('groot mes, bijv een broodmes');

INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, '', 'pompoen');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (125, 'gram', 'geitenkaas (Bettine blanc of Bucheron');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (85, 'gram', 'wilde kruiden (bijv zevenblad, wilde rucola, madeliefrozetjes');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (2, 'eetlepels', 'gesneden wilde kruiden voor vinaigrette (bijv pinksterbloem, smalle weegbree, waterpeper)');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (50, 'ml', 'rode wijn azijn');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (100, 'ml', 'olijfolie');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (0, '', 'zeezout & peper');

INSERT INTO instructions(instruction) VALUES ('Maak 1,5 uur van te voren een flink vuur. Laat alle stukken goed uitbranden tot alle grote stukken gloeiende kolen uit elkaar zijn gevallen in kleine kooltjes en hete as.');
INSERT INTO instructions(instruction) VALUES ('Maak een asbed en leg de pompoen er in.');
INSERT INTO instructions(instruction) VALUES ('Bedek de pompoen met as en kleine kooltjes.');
INSERT INTO instructions(instruction) VALUES ('Bak de pompoen tot de temperatuur binnenin 65 graden is. Dat duurt ongeveer 45-60 minuten');
INSERT INTO instructions(instruction) VALUES ('Maak ondertussen van de viaigrette van de klein gesneden wilde kruiden, azijn, olie, zout en peper.');
INSERT INTO instructions(instruction) VALUES ('Haal de pompoen uit het as als hij klaar is en laat hem even rusten.');
INSERT INTO instructions(instruction) VALUES ('Neem een lang mes en snij de pompoen horizontaal in tweeën.');
INSERT INTO instructions(instruction) VALUES ('Schraap met een lepel de pitten eruit.');
INSERT INTO instructions(instruction) VALUES ('Verkruimel de geitenkaas over de pompoenhelften.');
INSERT INTO instructions(instruction) VALUES ('Verdeel de rucola en de vinaigrette over de geitenkaas.');
INSERT INTO instructions(instruction) VALUES ('Schraap voorzichtig het pompoenvruchtvlees los van de schil en meng alles voorzichtig.');

INSERT INTO recipes (title, sub_title, persons, source, prep_time, cook_time) VALUES ('Pompoen met wilde kruiden', 'Ultiem, zoals een goede vriendin zegt', 4, 'https://natuurkok.nl/pompoen-met-wilde-kruiden/', '2 uur', '45-60 minuten');

UPDATE utensils SET recipe_id = 2 WHERE id = 2;
UPDATE utensils SET recipe_id = 2 WHERE id = 3;
UPDATE utensils SET recipe_id = 2 WHERE id = 4;
UPDATE utensils SET recipe_id = 2 WHERE id = 5;

UPDATE ingredients SET recipe_id = 2 WHERE id = 4;
UPDATE ingredients SET recipe_id = 2 WHERE id = 5;
UPDATE ingredients SET recipe_id = 2 WHERE id = 6;
UPDATE ingredients SET recipe_id = 2 WHERE id = 7;
UPDATE ingredients SET recipe_id = 2 WHERE id = 8;
UPDATE ingredients SET recipe_id = 2 WHERE id = 9;
UPDATE ingredients SET recipe_id = 2 WHERE id = 10;

UPDATE instructions SET recipe_id = 2 WHERE id = 4;
UPDATE instructions SET recipe_id = 2 WHERE id = 5;
UPDATE instructions SET recipe_id = 2 WHERE id = 6;
UPDATE instructions SET recipe_id = 2 WHERE id = 7;
UPDATE instructions SET recipe_id = 2 WHERE id = 8;
UPDATE instructions SET recipe_id = 2 WHERE id = 9;
UPDATE instructions SET recipe_id = 2 WHERE id = 10;
UPDATE instructions SET recipe_id = 2 WHERE id = 11;
UPDATE instructions SET recipe_id = 2 WHERE id = 12;
UPDATE instructions SET recipe_id = 2 WHERE id = 13;
UPDATE instructions SET recipe_id = 2 WHERE id = 14;

INSERT INTO recipe_months(recipe_id, months) VALUES (2, 'JAARROND');

INSERT INTO recipe_tags(recipe_id, tags) VALUES (2, 'VEGETARISCH');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (2, 'HOOFDGERECHT');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (2, 'OPENVUUR');


--Zevenbladpesto
INSERT INTO utensils (utensil) VALUES ('vijzel');

INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'teen', 'knoflook');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (55, 'gram', 'zevenbladblaadjes of basilicumblaadjes');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (20, 'gram', 'pijnboompitten of zonnebloempitten');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (55, 'gram', 'pecorino of Parmezaanse kaas, geraspt');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'deciliter', 'olijfolie');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (0.5, 'theelepel', 'grof zout');

INSERT INTO instructions(instruction) VALUES ('Pel de knoflook en snijd deze in dunne plakjes.');
INSERT INTO instructions(instruction) VALUES ('Pluk de zevenbladblaadjes en snijd deze fijn.');
INSERT INTO instructions(instruction) VALUES ('Doe zevenblad, knoflook, zout en pijnboompitten samen met een flinke scheut olijfolie in de vijzel en vijzel dit tot een groene pasta.');
INSERT INTO instructions(instruction) VALUES ('Voeg, al roerend, de rest van de olijfolie en de kaas toe.');

INSERT INTO recipes (title, sub_title, persons, source, prep_time, cook_time) VALUES ('Zevenbladpesto', 'Bij notenallergie kan je zonnebloempitten gebruiken ipv pijnboompitten', 0, 'https://www.kampvuurkok.nl/zevenbladpesto/', '45 minuten', '45 minuten');

UPDATE utensils SET recipe_id = 3 WHERE id = 6;

UPDATE ingredients SET recipe_id = 3 WHERE id = 11;
UPDATE ingredients SET recipe_id = 3 WHERE id = 12;
UPDATE ingredients SET recipe_id = 3 WHERE id = 13;
UPDATE ingredients SET recipe_id = 3 WHERE id = 14;
UPDATE ingredients SET recipe_id = 3 WHERE id = 15;
UPDATE ingredients SET recipe_id = 3 WHERE id = 16;

UPDATE instructions SET recipe_id = 3 WHERE id = 15;
UPDATE instructions SET recipe_id = 3 WHERE id = 16;
UPDATE instructions SET recipe_id = 3 WHERE id = 17;
UPDATE instructions SET recipe_id = 3 WHERE id = 18;

INSERT INTO recipe_months(recipe_id, months) VALUES (3, 'JAARROND');

INSERT INTO recipe_tags(recipe_id, tags) VALUES (3, 'VEGETARISCH');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (3, 'BIJGERECHT');


--Sloegin....................................................
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'liter', 'gin (of jenever, wodka, brandewijn');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (500, 'gram', 'sleedoorns');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (250, 'gram', 'basterdsuiker');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, '', 'kaneelstokje');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'theelepel', 'gedroogde jeneverbessen');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (0, 'paar', 'citroenschillen');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (0, 'scheutje', 'amandelolie');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (2, '', 'kruidnagels');

INSERT INTO instructions(instruction) VALUES ('Doe de verse bessen 2 dagen in de vriezer.');
INSERT INTO instructions(instruction) VALUES ('Voeg alle andere ingrediënten samen in een vat of pot.');
INSERT INTO instructions(instruction) VALUES ('Zet het vat/de pot afgesloten op een donkere plaats.');
INSERT INTO instructions(instruction) VALUES ('Laat het brouwsel ministens 10 weken staan. En roer het 1x per week door.');
INSERT INTO instructions(instruction) VALUES ('Zeef het met een koffiefilter. Dit kan best wat tijd in beslag nemen.');
INSERT INTO instructions(instruction) VALUES ('Doe de sloe gin in een fles.');

INSERT INTO recipes (title, sub_title, persons, source, prep_time, cook_time) VALUES ('Sloegin', 'Heerlijk in de winter', 0, 'https://www.landleven.nl/inspiratie/lekker-eten/2019/november/maak-een-heerlijke-borrel-sloe-gin/', '2 dagen', '10 weken');

UPDATE ingredients SET recipe_id = 4 WHERE id = 17;
UPDATE ingredients SET recipe_id = 4 WHERE id = 18;
UPDATE ingredients SET recipe_id = 4 WHERE id = 19;
UPDATE ingredients SET recipe_id = 4 WHERE id = 20;
UPDATE ingredients SET recipe_id = 4 WHERE id = 21;
UPDATE ingredients SET recipe_id = 4 WHERE id = 22;
UPDATE ingredients SET recipe_id = 4 WHERE id = 23;
UPDATE ingredients SET recipe_id = 4 WHERE id = 24;

UPDATE instructions SET recipe_id = 4 WHERE id = 19;
UPDATE instructions SET recipe_id = 4 WHERE id = 20;
UPDATE instructions SET recipe_id = 4 WHERE id = 21;
UPDATE instructions SET recipe_id = 4 WHERE id = 22;
UPDATE instructions SET recipe_id = 4 WHERE id = 23;
UPDATE instructions SET recipe_id = 4 WHERE id = 24;

INSERT INTO recipe_months(recipe_id, months) VALUES (4, 'OKTOBER');
INSERT INTO recipe_months(recipe_id, months) VALUES (4, 'NOVEMBER');

INSERT INTO recipe_tags(recipe_id, tags) VALUES (4, 'VEGETARISCH');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (4, 'LACTOSEVRIJ');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (4, 'ALCOHOLISCH');


--Brandnetelsoep....................................................
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'emmertje', 'vers geplukte jonge brandnetelbladeren');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, '', 'ui');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, '', 'aardappel');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'liter', 'water');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, '', 'bouillonblokje (voor vegetariers een groentenbouillonblokje)');

INSERT INTO instructions(instruction) VALUES ('Was de brandnetels goed. ');
INSERT INTO instructions(instruction) VALUES ('Snij het harde steeltje af en snij de blaadjes in stukjes.');
INSERT INTO instructions(instruction) VALUES ('Schil de aardappel en snij deze in stukjes.');
INSERT INTO instructions(instruction) VALUES ('Pel en snipper de ui.');
INSERT INTO instructions(instruction) VALUES ('Fruit eerst het uitje in een beetje olie.');
INSERT INTO instructions(instruction) VALUES ('Doe daarna de brandnetel erbij, de aardappelblokjes, een liter water en een bouillonblokje.');
INSERT INTO instructions(instruction) VALUES ('De aardappel zorgt ervoor dat de soep een beetje dikker wordt.');
INSERT INTO instructions(instruction) VALUES ('Laat het mengsel een kwartiertje pruttelen, tot de aardappel gaar is.');
INSERT INTO instructions(instruction) VALUES ('Haal dan de pan van het vuur en pureer de soep met een staafmixer.');

INSERT INTO recipes (title, sub_title, persons, source, prep_time, cook_time) VALUES ('Brandnetelsoep', '', 4, 'https://www.natuurmonumenten.nl/kinderen/zelf-spelen/maak-zelf-brandnetelsoep', '', '30 minuten');

UPDATE ingredients SET recipe_id = 5 WHERE id = 25;
UPDATE ingredients SET recipe_id = 5 WHERE id = 26;
UPDATE ingredients SET recipe_id = 5 WHERE id = 27;
UPDATE ingredients SET recipe_id = 5 WHERE id = 28;
UPDATE ingredients SET recipe_id = 5 WHERE id = 29;

UPDATE instructions SET recipe_id = 5 WHERE id = 25;
UPDATE instructions SET recipe_id = 5 WHERE id = 26;
UPDATE instructions SET recipe_id = 5 WHERE id = 27;
UPDATE instructions SET recipe_id = 5 WHERE id = 28;
UPDATE instructions SET recipe_id = 5 WHERE id = 29;
UPDATE instructions SET recipe_id = 5 WHERE id = 30;
UPDATE instructions SET recipe_id = 5 WHERE id = 31;
UPDATE instructions SET recipe_id = 5 WHERE id = 32;
UPDATE instructions SET recipe_id = 5 WHERE id = 33;

INSERT INTO recipe_months(recipe_id, months) VALUES (5, 'JAARROND');

INSERT INTO recipe_tags(recipe_id, tags) VALUES (5, 'VEGETARISCH');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (5, 'VEGANISTISCH');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (5, 'LACTOSEVRIJ');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (5, 'VOORGERECHT');


--Vlierbloesemchampagne................................................
INSERT INTO utensils (utensil) VALUES ('2 of 3 beugelflessen');
INSERT INTO utensils (utensil) VALUES ('schone thee- of kaasdoek');

INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (20, '', 'vlierbloesemschermen');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, '', 'biologische citroen');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (250, 'gram', 'suiker');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (1, 'eetlepel', 'appel- of witte wijnazijn');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (2, 'liter', 'koud water');
INSERT INTO ingredients (amount, unit, ingredient_name) VALUES (0.5, 'theelepel', 'gist (eventueel na paar dagen)');

INSERT INTO instructions(instruction) VALUES ('Knip boven een kom de bloesem van de (ongewassen) schermen.');
INSERT INTO instructions(instruction) VALUES ('Haal eventuele insecten eruit.');
INSERT INTO instructions(instruction) VALUES ('Snijd de citroen in stukken en voeg toe, samen met suiker, azijn en water.');
INSERT INTO instructions(instruction) VALUES ('Roer goed door.');
INSERT INTO instructions(instruction) VALUES ('Bedek de kom met een doek en zet weg op een niet te koude plek (dus niet in de koelkast).');
INSERT INTO instructions(instruction) VALUES ('Roer af en toe.');
INSERT INTO instructions(instruction) VALUES ('Na een paar dagen komt het gistingsproces spontaan op gang: de boel begint lichtjes te bubbelen.');
INSERT INTO instructions(instruction) VALUES ('Zo niet: voeg de gist toe en laat nog een dag staan.');
INSERT INTO instructions(instruction) VALUES ('Bottel in beugelflessen. Vul de flessen niet helemaal af vanwege de druk die onstaat. Laat nog een paar dagen staan voor een heerlijk verfrissend licht-alcoholisch drankje met bubbels.');
INSERT INTO instructions(instruction) VALUES ('Met wel een waarschuwing voor ontploffingsgevaar: controleer regelmatig of er niet te veel druk op de gebottelde champagne staat door de buegelflessen kort te openen en weer te sluiten.');

INSERT INTO recipes (title, sub_title, persons, source, prep_time, cook_time) VALUES ('Vlierbloesemchampagne', 'Lekkere voorjaarsbubbels', 0, 'https://downtoearthmagazine.nl/vlierbloesemchampagne/', '20 minuten', '1 - 3 weken');

UPDATE utensils SET recipe_id = 6 WHERE id = 7;
UPDATE utensils SET recipe_id = 6 WHERE id = 8;

UPDATE ingredients SET recipe_id = 6 WHERE id = 30;
UPDATE ingredients SET recipe_id = 6 WHERE id = 31;
UPDATE ingredients SET recipe_id = 6 WHERE id = 32;
UPDATE ingredients SET recipe_id = 6 WHERE id = 33;
UPDATE ingredients SET recipe_id = 6 WHERE id = 34;
UPDATE ingredients SET recipe_id = 6 WHERE id = 35;

UPDATE instructions SET recipe_id = 6 WHERE id = 34;
UPDATE instructions SET recipe_id = 6 WHERE id = 35;
UPDATE instructions SET recipe_id = 6 WHERE id = 36;
UPDATE instructions SET recipe_id = 6 WHERE id = 37;
UPDATE instructions SET recipe_id = 6 WHERE id = 38;
UPDATE instructions SET recipe_id = 6 WHERE id = 39;
UPDATE instructions SET recipe_id = 6 WHERE id = 40;
UPDATE instructions SET recipe_id = 6 WHERE id = 41;
UPDATE instructions SET recipe_id = 6 WHERE id = 42;
UPDATE instructions SET recipe_id = 6 WHERE id = 43;

INSERT INTO recipe_months(recipe_id, months) VALUES (6, 'MEI');
INSERT INTO recipe_months(recipe_id, months) VALUES (6, 'JUNI');

INSERT INTO recipe_tags(recipe_id, tags) VALUES (6, 'VEGETARISCH');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (6, 'LACTOSEVRIJ');
INSERT INTO recipe_tags(recipe_id, tags) VALUES (6, 'ALCOHOLISCH');


