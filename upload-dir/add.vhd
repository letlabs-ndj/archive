LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY FA IS PORT (
    ci, xi, yi: IN BIT;
    co, si: OUT BIT);
END FA;
ARCHITECTURE Dataflow OF FA IS
BEGIN
    co <= (xi AND yi) OR (ci AND (xi XOR yi));
    si <= xi XOR yi XOR ci;
END Dataflow;