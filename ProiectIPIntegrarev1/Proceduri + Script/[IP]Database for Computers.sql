DROP TABLE Computers;
/
CREATE TABLE Computers (
IP_Adress varchar2(15) NOT NULL,
port number(5) NOT NULL,
input NUMBER(2) NOT NULL,
output NUMBER(2) NOT NULL,
tipFunctie number(1) NOT NULL,
status NUMBER(1) NOT NULL,
numeInput VARCHAR2(100),
numeOutput VARCHAR2(100)
)
/
ALTER TABLE Computers ADD CONSTRAINT 
  pk_computers PRIMARY KEY (port);
/
/*
oras, stat(11) -> vreme(12) ---------- TIP 1  6790
coordonate(13) -> oras, stat(11) --------- TIP 2 6792
oras, stat(11) -> coordonate(13) --------- TIP 4  6789
oras, district(14) -> coordonate(13) --------- TIP 3 6788

TIPURI INPUT/OUTPUT
oras, stat -> TIP INPUT 11
vreme -> TIP INPUT 12
coordonate -> TIP INPUT 13
oras, district -> TIP INPUT 14
*/
--INSERT INTO Computers DATA (IP_Adress, port, input, output, tipFunctie, status) VALUES ('1', 6787, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6788, 14, 13, 3, 0, 'cityDistrict','coordinates');
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6789, 11, 13, 4, 0, 'cityState','coordinates');
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0, 'cityState', 'weather');

--INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6792, 13, 11, 2, 0,'coordinates', 'cityState');

--INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0);
/*INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6792, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6788, 14, 13, 3, 0);

--INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6792, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6789, 11, 13, 4, 0);

--INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6788, 14, 13, 3, 0);*/

COMMIT;

SELECT * FROM COMPUTERS;