library ieee;
use ieee.std_logic_1164.all;

entity TesteROM is
   PORT(
		CLK : IN  STD_LOGIC;
		RST : IN  STD_LOGIC
	);

end entity;


ARCHITECTURE arch OF TesteROM IS

COMPONENT ROM IS
	PORT
	(
		address		: IN STD_LOGIC_VECTOR (14 DOWNTO 0);
		clock		: IN STD_LOGIC  := '1';
		q		: OUT STD_LOGIC_VECTOR (15 DOWNTO 0)
	);
END COMPONENT;

BEGIN

	U0 : ROM PORT MAP (
		address  => "000000000000000",
		clock    => CLK,
		q        => open
	);


END arch;

