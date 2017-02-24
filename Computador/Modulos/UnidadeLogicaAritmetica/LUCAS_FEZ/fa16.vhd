library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity fa16 is
	port(a, b: in STD_LOGIC_VECTOR(15 downto 0);
		  ca: out STD_LOGIC;
		  sum: out STD_LOGIC_VECTOR(15 downto 0)
		  );
end fa16;

architecture data of fa16 is

Component fa1 is
	port(
	a,b,c: in STD_LOGIC;
	sum,ca: out STD_LOGIC);
	end component;
	
	signal s: std_logic_vector(14 downto 0);
	signal first: std_logic;
	
begin
	first<='0';
	u0 : fa1 port map (a(0),b(0),first,sum(0),s(0));
	u1 : fa1 port map (a(1),b(1),s(0),sum(1),s(1));
	u2 : fa1 port map (a(2),b(2),s(1),sum(2),s(2));
	u3 : fa1 port map (a(3),b(3),s(2),sum(3),s(3));
	u4 : fa1 port map (a(4),b(4),s(3),sum(4),s(4));
	u5 : fa1 port map (a(5),b(5),s(4),sum(5),s(5));
	u6 : fa1 port map (a(6),b(6),s(5),sum(6),s(6));
	u7 : fa1 port map (a(7),b(7),s(6),sum(7),s(7));
	u8 : fa1 port map (a(8),b(8),s(7),sum(8),s(8));
	u9 : fa1 port map (a(9),b(9),s(8),sum(9),s(9));
	u10 : fa1 port map (a(10),b(10),s(9),sum(10),s(10));
	u11 : fa1 port map (a(11),b(11),s(10),sum(11),s(11));
	u12 : fa1 port map (a(12),b(12),s(11),sum(12),s(12));
	u13 : fa1 port map (a(13),b(13),s(12),sum(13),s(13));
	u14 : fa1 port map (a(14),b(14),s(13),sum(14),s(14));
	u15 : fa1 port map (a(15),b(15),s(14),sum(15),ca);
end data;
	
