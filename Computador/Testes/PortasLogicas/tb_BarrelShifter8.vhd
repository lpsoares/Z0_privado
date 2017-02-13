library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_BarrelShifter8 is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_BarrelShifter8 is

	component BarrelShifter8 is
	port ( 
			a:   in  STD_LOGIC_VECTOR(7 downto 0);
			q:   out STD_LOGIC_VECTOR(7 downto 0));
	end component;
	
begin

  main : process
  begin
    test_runner_setup(runner, runner_cfg);


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;