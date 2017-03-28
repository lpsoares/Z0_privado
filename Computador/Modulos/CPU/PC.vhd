library ieee;
use ieee.std_logic_1164.all;
use IEEE.NUMERIC_STD.ALL;

entity pc is
   port(
         clock     : in  STD_LOGIC;
			increment : in  STD_LOGIC;
			load      : in  STD_LOGIC;
			reset     : in  STD_LOGIC;
         input     : in  std_logic_vector(15 downto 0);
         output    : out std_logic_vector(14 downto 0)
 );
end pc;

architecture logic of pc is
begin
 process(clock,reset) 
 variable count : std_logic_vector(14 downto 0) := "000000000000000";
 begin
  if rising_edge(clock) then
   if reset = '1' then
    count := (others => '0');
   else
	 if load = '1' then
	  count := std_logic_vector(to_unsigned(to_integer(unsigned(input)), 15));
	 else
	  if increment = '1' then
		count := std_logic_vector(to_unsigned(to_integer(unsigned( count )) + 1, 15));
		
		-- TESTE REMOVER
		--if to_integer(unsigned( count )) = 20 then
		--	count := (others => '0');
		--end if;
		
	  end if;
	 end if;
   end if;
  end if;
  output <= count;
 end process;
end logic;