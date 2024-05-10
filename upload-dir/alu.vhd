LIBRARY ieee;
USE ieee.std_logic_1164.all;
-- The following package is needed so that the STD_LOGIC_VECTOR signals
-- A and B can be used in unsigned arithmetic operations.
USE ieee.std_logic_unsigned.all;
ENTITY alu IS PORT (
    S: IN std_logic_vector(2 downto 0); -- select for operations
    A, B: IN std_logic_vector(3 downto 0); -- input operands
    F: OUT std_logic_vector(3 downto 0)); -- output
END alu;
ARCHITECTURE Behavior OF alu IS
BEGIN
    PROCESS(S, A, B)
    BEGIN
        CASE S IS
        WHEN "000" => -- pass A through
            F<=A;
        WHEN "001" => -- AND
            F <= A AND B;
        WHEN "010" => -- OR
            F<=A OR B;
        WHEN "011" => -- NOT A
            F <= NOT A;
        WHEN "100" => -- add
            F<=A+B;
        WHEN "101" => -- subtract
            F<=A-B;
        WHEN "110" => -- increment
            F<=A+1;
        WHEN OTHERS => -- decrement
            F<=A-1;
        END CASE;
    END PROCESS;
END Behavior;