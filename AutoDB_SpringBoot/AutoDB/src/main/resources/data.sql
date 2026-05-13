-- Márkák
INSERT INTO marka (nev, orszag, alapitas_eve) VALUES ('BMW', 'Németország', 1916);
INSERT INTO marka (nev, orszag, alapitas_eve) VALUES ('Toyota', 'Japán', 1937);
INSERT INTO marka (nev, orszag, alapitas_eve) VALUES ('Ford', 'USA', 1903);
INSERT INTO marka (nev, orszag, alapitas_eve) VALUES ('Mercedes', 'Németország', 1926);
INSERT INTO marka (nev, orszag, alapitas_eve) VALUES ('Audi', 'Németország', 1909);

-- Modellek (marka_id: 1=BMW, 2=Toyota, 3=Ford, 4=Mercedes, 5=Audi)
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (1, '3-as sorozat', 2020, 'Szedán', 1998);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (1, 'X5', 2021, 'SUV', 2998);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (1, 'M4', 2022, 'Sportautó', 2993);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (2, 'Corolla', 2019, 'Szedán', 1798);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (2, 'RAV4', 2021, 'SUV', 2494);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (2, 'GR Supra', 2020, 'Sportautó', 2998);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (3, 'Mustang', 2021, 'Sportautó', 4951);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (3, 'Focus', 2018, 'Kombi', 1596);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (4, 'C-osztály', 2022, 'Szedán', 1991);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (4, 'GLE', 2021, 'SUV', 2999);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (5, 'A4', 2020, 'Szedán', 1984);
INSERT INTO modell (marka_id, nev, ev, kategoria, motor_cm3) VALUES (5, 'Q7', 2022, 'SUV', 2967);

-- Tulajdonosok (marka_id: 1=BMW, 2=Toyota, 3=Ford)
INSERT INTO tulajdonos (nev, email, telefonszam, marka_id)
    VALUES ('Kovács Péter', 'kovacs.peter@email.hu', '+36301234567', 1);
INSERT INTO tulajdonos (nev, email, telefonszam, marka_id)
    VALUES ('Nagy Anna', 'nagy.anna@email.hu', '+36209876543', 2);
INSERT INTO tulajdonos (nev, email, telefonszam, marka_id)
    VALUES ('Szabó János', 'szabo.janos@email.hu', '+36701112233', 3);
INSERT INTO tulajdonos (nev, email, telefonszam, marka_id)
    VALUES ('Tóth Éva', 'toth.eva@email.hu', '+36305554433', 1);
INSERT INTO tulajdonos (nev, email, telefonszam, marka_id)
    VALUES ('Fekete Gábor', 'fekete.gabor@email.hu', '+36209998877', 2);
