DROP TABLE Computers;
/
CREATE TABLE Computers (
IP_Adress varchar2(15) NOT NULL,
port number(5) NOT NULL,
input NUMBER(2) NOT NULL,
output NUMBER(2) NOT NULL,
tipFunctie number(1) NOT NULL,
status NUMBER(1) NOT NULL
)
/
ALTER TABLE Computers ADD CONSTRAINT 
  pk_computers PRIMARY KEY (port);
/
/*
oras, stat(11) -> vreme(12) ---------- TIP 1
coordonate(13) -> oras, stat(11) --------- TIP 2
oras, stat(11) -> coordonate(13) --------- TIP 4
oras, district(14) -> coordonate(13) --------- TIP 3

TIPURI INPUT/OUTPUT
oras, stat -> TIP INPUT 11
vreme -> TIP INPUT 12
coordonate -> TIP INPUT 13
oras, district -> TIP INPUT 14
*/
INSERT INTO Computers DATA (IP_Adress, port, input, output, tipFunctie, status) VALUES ('1', 6787, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('1', 6788, 14, 13, 3, 0);
INSERT INTO Computers DATA  VALUES ('1', 6789, 11, 13, 4, 0);
INSERT INTO Computers DATA  VALUES ('1', 6790, 11, 12, 1, 0);

INSERT INTO Computers DATA  VALUES ('2', 6791, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('2', 6792, 13, 11, 2, 0);

INSERT INTO Computers DATA  VALUES ('3', 6794, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('3', 6795, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('3', 6796, 14, 13, 3, 0);

INSERT INTO Computers DATA  VALUES ('4', 6797, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('4', 6798, 13, 11, 2, 0);
INSERT INTO Computers DATA  VALUES ('4', 6799, 11, 13, 4, 0);

INSERT INTO Computers DATA  VALUES ('5', 6800, 11, 12, 1, 0);
INSERT INTO Computers DATA  VALUES ('5', 6801, 14, 13, 3, 0);

COMMIT;