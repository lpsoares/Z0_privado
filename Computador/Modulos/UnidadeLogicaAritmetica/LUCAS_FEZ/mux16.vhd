library IEEE; 
use IEEE.STD_LOGIC_1164.all;
  
entity mux16 is
   port(sel : in STD_LOGIC;
	     a   : in STD_LOGIC_VECTOR(15 downto 0);
        b   : in STD_LOGIC_VECTOR(15 downto 0);
        y   : out STD_LOGIC_VECTOR(15 downto 0) 
   ); 
end mux16; 
 
architecture strucMux16 of mux16 is
   component mux1 is
      port(
	   sel,a,b:in STD_LOGIC;
	   y:out STD_LOGIC);
   end component;       
begin 
   u0 : mux1 port map (sel, a( 0),b( 0),y( 0)); 
   u1 : mux1 port map (sel, a( 1),b( 1),y( 1)); 
   u2 : mux1 port map (sel, a( 2),b( 2),y( 2)); 
   u3 : mux1 port map (sel, a( 3),b( 3),y( 3)); 
   u4 : mux1 port map (sel, a( 4),b( 4),y( 4)); 
   u5 : mux1 port map (sel, a( 5),b( 5),y( 5)); 
   u6 : mux1 port map (sel, a( 6),b( 6),y( 6)); 
   u7 : mux1 port map (sel, a( 7),b( 7),y( 7)); 
   u8 : mux1 port map (sel, a( 8),b( 8),y( 8)); 
   u9 : mux1 port map (sel, a( 9),b( 9),y( 9)); 
   u10: mux1 port map (sel, a(10),b(10),y(10)); 
   u11: mux1 port map (sel, a(11),b(11),y(11)); 
   u12: mux1 port map (sel, a(12),b(12),y(12)); 
   u13: mux1 port map (sel, a(13),b(13),y(13)); 
   u14: mux1 port map (sel, a(14),b(14),y(14)); 
   u15: mux1 port map (sel, a(15),b(15),y(15)); 

	end strucMux16;