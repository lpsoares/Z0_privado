Library ieee;
use ieee.std_logic_1164.all;




entity ha is

	port(
	a,b:in STD_LOGIC;
	sum,ca: out STD_LOGIC);
	
end ha;

architecture data of ha is
	
begin 
	sum <= a xor b;
	ca <= a and b;
	
end data;