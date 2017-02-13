library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_BarrelShifter16 is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_BarrelShifter16 is


component BarrelShifter16 is
	port ( 
			a:   in  STD_LOGIC_VECTOR(15 downto 0);
			q:   out STD_LOGIC_VECTOR(15 downto 0));
end component;

begin

  main : process
  begin
    test_runner_setup(runner, runner_cfg);


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;