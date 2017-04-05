-- Elementos de Sistemas
-- developed by Luciano Soares
-- file: PC.vhd
-- date: 4/4/2017

-- Contador de 16bits
-- if (reset[t] == 1) out[t+1] = 0
-- else if (load[t] == 1)  out[t+1] = in[t]
-- else if (inc[t] == 1) out[t+1] = out[t] + 1
-- else out[t+1] = out[t]

library ieee;
use ieee.std_logic_1164.all;
use IEEE.NUMERIC_STD.ALL;

entity PC is
    port(
        clock     : in  STD_LOGIC;
		increment : in  STD_LOGIC;
		load      : in  STD_LOGIC;
		reset     : in  STD_LOGIC;
        input     : in  STD_LOGIC_VECTOR(15 downto 0);
        output    : out STD_LOGIC_VECTOR(14 downto 0)
    );
end entity;

architecture arch of PC is
begin

	process(clock,reset) 
		variable count : STD_LOGIC_VECTOR(14 downto 0) := "000000000000000";
	begin
		if rising_edge(clock) then
   			if reset = '1' then
    			count := (others => '0');
   			elsif load = '1' then
	  			count := STD_LOGIC_VECTOR(to_unsigned(to_integer(unsigned(input)), 15));
	 		elsif increment = '1' then
				count := STD_LOGIC_VECTOR(to_unsigned(to_integer(unsigned( count )) + 1, 15));
	  		end if;
	 	end if;
		output <= count;
	end process;

end architecture;