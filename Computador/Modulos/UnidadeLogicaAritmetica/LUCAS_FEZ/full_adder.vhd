Library ieee;
use ieee.std_logic_1164.all;

entity fa1 is

	port(
	a,b,c: in STD_LOGIC;
	sum,ca: out STD_LOGIC);
	
end fa1;

architecture data of fa1 is
	
begin 
	sum <= a xor b xor c;
	ca <= ((a and b) or (b and c) or (a and c));
	
end data;