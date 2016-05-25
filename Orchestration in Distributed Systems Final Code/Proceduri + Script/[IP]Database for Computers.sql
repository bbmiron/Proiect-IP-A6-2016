DROP TABLE Computers;
/
CREATE TABLE Computers (
IP_Adress varchar2(15) NOT NULL,
port number(5) NOT NULL,
input NUMBER(2) NOT NULL,
output NUMBER(2) NOT NULL,
tipFunctie number(3) NOT NULL,
status NUMBER(1) NOT NULL,
numeInput VARCHAR2(100),
numeOutput VARCHAR2(100)
)
/
ALTER TABLE Computers ADD CONSTRAINT 
  pk_computers PRIMARY KEY (IP_Adress, port);
/
/*
oras, stat(11) -> vreme(12) ---------- TIP 1  6790
coordonate(13) -> oras, stat(11) --------- TIP 2 6792
oras, stat(11) -> coordonate(13) --------- TIP 4  6789
oras, district(14) -> coordonate(13) --------- TIP 3 6788
oras(11) -> populatie(15) --------- TIP 5 6788


TIPURI INPUT/OUTPUT
oras, stat -> TIP INPUT 11
vreme -> TIP INPUT 12
coordonate -> TIP INPUT 13
oras, district -> TIP INPUT 14
*/
--INSERT INTO Computers DATA (IP_Adress, port, input, output, tipFunctie, status) VALUES ('1', 6787, 13, 11, 2, 0);
/*INSERT INTO Computers DATA  VALUES ('172.17.50.56', 6799, 11, 12, 1, 0, 'cityState', 'weather');
INSERT INTO Computers DATA  VALUES ('172.17.50.56', 6800, 13, 11, 2, 0,'coordinates', 'cityState');
INSERT INTO Computers DATA  VALUES ('172.17.50.56', 6797, 14, 13, 3, 0, 'cityDistrict','coordinates');
INSERT INTO Computers DATA  VALUES ('172.17.50.56', 6798, 11, 13, 4, 0, 'cityState','coordinates');*/
/*
INSERT INTO Computers DATA  VALUES ('10.1.14.134', 6790, 11, 12, 1, 0, 'cityState', 'weather');
INSERT INTO Computers DATA  VALUES ('10.1.14.134', 6792, 13, 11, 2, 0,'coordinates', 'cityState');
INSERT INTO Computers DATA  VALUES ('10.1.14.134', 6788, 14, 13, 3, 0, 'cityDistrict','coordinates');
INSERT INTO Computers DATA  VALUES ('10.1.14.134', 6789, 11, 13, 4, 0, 'cityState','coordinates');

;*/

--INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0);
/*INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6792, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6788, 14, 13, 3, 0);

--INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6792, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6789, 11, 13, 4, 0);

--INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6790, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('85.122.23.57', 6788, 14, 13, 3, 0);*/


INSERT INTO Computers DATA  VALUES ('172.17.50.25', 6803, 11, 12, 1, 0, 'cityState', 'weather');
INSERT INTO Computers DATA  VALUES ('172.17.50.25', 6804, 13, 11, 2, 0,'coordinates', 'cityState');
INSERT INTO Computers DATA  VALUES ('172.17.50.25', 6801, 14, 13, 3, 0, 'cityDistrict','coordinates');
INSERT INTO Computers DATA  VALUES ('172.17.50.25', 6805, 11, 15, 5, 0, 'oras','populatie');
INSERT INTO Computers DATA  VALUES ('172.17.50.25', 6802, 11, 13, 4, 0, 'cityState','coordinates');

INSERT INTO Computers DATA  VALUES ('172.17.50.24', 6803, 11, 12, 1, 0, 'cityState', 'weather');
INSERT INTO Computers DATA  VALUES ('172.17.50.24', 6804, 13, 11, 2, 0,'coordinates', 'cityState');
INSERT INTO Computers DATA  VALUES ('172.17.50.24', 6801, 14, 13, 3, 0, 'cityDistrict','coordinates');
INSERT INTO Computers DATA  VALUES ('172.17.50.24', 6805, 11, 15, 5, 0, 'oras','populatie');
INSERT INTO Computers DATA  VALUES ('172.17.50.24', 6802, 11, 13, 4, 0, 'cityState','coordinates');
/
COMMIT;

SELECT * FROM COMPUTERS;