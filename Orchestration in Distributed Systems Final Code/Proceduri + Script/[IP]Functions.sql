SET SERVEROUTPUT ON;

CREATE OR REPLACE FUNCTION add_function(p_ip_adress COMPUTERS.IP_ADRESS%TYPE,p_port COMPUTERS.PORT%TYPE, p_input COMPUTERS.INPUT%TYPE, p_output COMPUTERS.OUTPUT%TYPE, p_tipFunctie COMPUTERS.TIPFUNCTIE%TYPE,  p_denumireInput COMPUTERS.NUMEINPUT%TYPE,p_denumireOutput COMPUTERS.NUMEOUTPUT%TYPE) RETURN BOOLEAN IS
BEGIN
  INSERT INTO Computers VALUES (p_ip_adress, p_port, p_input, p_output, p_tipFunctie, 0, p_denumireInput ,p_denumireOutput);
  RETURN TRUE;
END add_function;
/
CREATE OR REPLACE FUNCTION remove_computer(p_ip_adress COMPUTERS.IP_ADRESS%TYPE, p_port COMPUTERS.PORT%TYPE) RETURN BOOLEAN IS
CURSOR c_calculatoare IS SELECT IP_Adress, port FROM COMPUTERS;
BEGIN
  FOR i IN c_calculatoare LOOP
    IF i.IP_Adress = p_ip_adress THEN
      DELETE FROM Computers WHERE IP_ADRESS = p_ip_adress AND port = p_port;
      RETURN TRUE;
    END IF;
    END LOOP;
    RETURN FALSE;
END remove_computer;
/
CREATE OR REPLACE FUNCTION java_add_calc(p_ip_adress COMPUTERS.IP_ADRESS%TYPE,p_port COMPUTERS.PORT%TYPE, p_input COMPUTERS.INPUT%TYPE, p_output COMPUTERS.OUTPUT%TYPE, p_tipFunctie COMPUTERS.TIPFUNCTIE%TYPE, p_denumireInput COMPUTERS.NUMEINPUT%TYPE,p_denumireOutput COMPUTERS.NUMEOUTPUT%TYPE) RETURN VARCHAR2 IS
Exits BOOLEAN;
BEGIN
  Exits := add_function(p_ip_adress, p_port, p_input, p_output, p_tipFunctie, p_denumireInput,p_denumireOutput);
  IF Exits THEN
  RETURN 'True';
  ELSE
  RETURN 'False';
  END IF;
END java_add_calc;
/
CREATE OR REPLACE FUNCTION java_rm_calc(p_ip_adress COMPUTERS.IP_ADRESS%TYPE, p_port COMPUTERS.PORT%TYPE) RETURN VARCHAR2 IS
Exits BOOLEAN;
BEGIN
  Exits := remove_computer(p_ip_adress, p_port);
  IF Exits THEN
  RETURN 'True';
  ELSE
  RETURN 'False';
  END IF;
END java_rm_calc;
/
CREATE OR REPLACE FUNCTION modify_status(p_port NUMBER, p_ip VARCHAR2) RETURN NUMBER IS 
v_temp COMPUTERS.STATUS%TYPE;
BEGIN
  SELECT status INTO v_temp FROM COMPUTERS WHERE COMPUTERS.PORT = p_port AND COMPUTERS.IP_Adress = p_ip;
  IF v_temp = 0 THEN
    UPDATE COMPUTERS SET status = 1 WHERE PORT = p_port AND COMPUTERS.IP_Adress = p_ip;
    RETURN 1;
  ELSE
    UPDATE COMPUTERS SET status = 0 WHERE PORT = p_port AND COMPUTERS.IP_Adress = p_ip;
    RETURN 1;
  END IF;
  RETURN 0;
END modify_status;
/
COMMIT;
CREATE OR REPLACE FUNCTION java_modify_status(p_port COMPUTERS.PORT%TYPE) RETURN VARCHAR2 IS
Exits BOOLEAN;
BEGIN
Exits := modify_status(p_port);
IF Exits THEN
  RETURN 'True';
  ELSE
  RETURN 'False';
  END IF;
END java_modify_status;
/
CREATE OR REPLACE FUNCTION get_status(p_port NUMBER, p_ip VARCHAR2) RETURN INTEGER IS
v_temp NUMBER(2);
BEGIN
  SELECT status INTO v_temp FROM COMPUTERS WHERE COMPUTERS.PORT = p_port AND COMPUTERS.IP_Adress = p_ip;
  RETURN v_temp;
END get_status;
/
DECLARE
  v1 COMPUTERS.IP_ADRESS%TYPE := '7';
  port number(5) := 7776;
  inr int := 99;
  outr int := 88;
  tipf int := 0;
  bo BOOLEAN;
  rm BOOLEAN;
  testt INTEGER;
BEGIN
  rm := remove_computer(v1, port);
  
  IF rm = TRUE THEN
  DBMS_OUTPUT.PUT_LINE('rm');
  ELSE 
  DBMS_OUTPUT.PUT_LINE('no rm');
  END IF;
  
END;
/

BEGIN 
 DBMS_OUTPUT.PUT_LINE(get_status(6802, '172.17.50.24'));
END;
/
--alter system set open_cursors = 1000 

COMMIT;