LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
ENTITY MooreFSM IS PORT(
    clock: IN STD_LOGIC;
    reset: IN STD_LOGIC;
    start, neq9: IN STD_LOGIC;
    x,y: OUT STD_LOGIC);
END MooreFSM;
ARCHITECTURE Behavioral OF MooreFSM IS
    TYPE state_type IS (s0, s1, s2, s3);
    SIGNAL state: state_type;
BEGIN
    next_state_logic: PROCESS (clock, reset)
    BEGIN
        IF (reset = '1') THEN
            state <= s0;
        ELSIF (clock'EVENT AND clock = '1') THEN
            CASE state IS
            WHEN s0 =>
                IF start = '1' THEN
                    state <= s1;
                ELSE
                    state <= s0;
                END IF;
            WHEN s1 =>
                state <= s2;
            WHEN s2 =>
                IF neq9 = '1' THEN
                    state <= s3;
                ELSE
                    state <= s1;
                END IF;
            WHEN s3 =>
                state <= s0;
            END CASE;
        END IF;
    END PROCESS;
    output_logic: PROCESS (state)
    BEGIN
        CASE state IS
        WHEN s0 =>
            x <= '0';
            y <= '1';
        WHEN s1 =>
            x <= '1';
            y <= '1';
        WHEN s2 =>
            x <= '1';
            y <= '1';
        WHEN s3 =>
            x <= '1';
            y <= '0';
        END CASE;
    END PROCESS;
END Behavioral;