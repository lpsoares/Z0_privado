Library ieee; 
use ieee.std_logic_1164.all;
  
entity HalfAdderLuciano is
   port(
	a,b:in bit;
	sum,carry:out bit
	); 
end HalfAdderLuciano; 
 
architecture data of HalfAdderLuciano is
begin
   sum <= (not a) xor (not b);  
   carry <= (not a) and (not b);  
end data; 