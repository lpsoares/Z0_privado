-- Elementos de Sistemas
-- by Luciano Soares
-- BarrelShifter8.vhd

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.numeric_std.all;

entity BarrelShifter8 is
	port ( 
			a:    in  STD_LOGIC_VECTOR(7 downto 0);   -- input vector
			dir:  in  std_logic;                       -- 0=>left 1=>right
			size: in  std_logic_vector(2 downto 0);    -- shift amount
			q:    out STD_LOGIC_VECTOR(7 downto 0));  -- output vector (shifted)
end entity;

architecture arch of BarrelShifter8 is
begin

	with dir select  
      	q <= std_logic_vector(shift_left(unsigned(a),to_integer(unsigned(size)))) when '0',
      	     std_logic_vector(shift_right(unsigned(a), to_integer(unsigned(size)))) when others;



end architecture;