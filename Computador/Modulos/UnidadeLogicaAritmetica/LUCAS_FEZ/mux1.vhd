Library ieee; 
use ieee.std_logic_1164.all;
  
entity mux1 is
   port(
	sel,a,b:in STD_LOGIC;
	y:out STD_LOGIC);
end mux1;
  
architecture data of mux1 is
begin 
   y <= (not sel and a) or (sel and b); 
end data;