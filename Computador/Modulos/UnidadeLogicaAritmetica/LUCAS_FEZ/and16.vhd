library IEEE; 
use IEEE.STD_LOGIC_1164.all;
  
entity and16 is
   port(a   : in STD_LOGIC_VECTOR(15 downto 0);
        b   : in STD_LOGIC_VECTOR(15 downto 0);
        y   : out STD_LOGIC_VECTOR(15 downto 0) 
   ); 
end and16; 
 
architecture strucAND16 of and16 is
begin 
   y(0) <= a(0) and b(0);
   y(1) <= a(1) and b(1);
	y(2) <= a(2) and b(2);
	y(3) <= a(3) and b(3);
	y(4) <= a(4) and b(4);
	y(5) <= a(5) and b(5);
	y(6) <= a(6) and b(6);
	y(7) <= a(7) and b(7);
	y(8) <= a(8) and b(8);
	y(9) <= a(9) and b(9);
	y(10) <= a(10) and b(10);
	y(11) <= a(11) and b(11);
	y(12) <= a(12) and b(12);
	y(13) <= a(13) and b(13);
	y(14) <= a(14) and b(14);
	y(15) <= a(15) and b(15);
 
end strucAND16;