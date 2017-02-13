library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_DMux4Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_DMux4Way is

component DMux4Way is
	port ( 
			a:   in  STD_LOGIC;
			sel: in  STD_LOGIC_VECTOR(1 downto 0);
			q0:  out STD_LOGIC;
			q1:  out STD_LOGIC;
			q2:  out STD_LOGIC;
			q3:  out STD_LOGIC);
end component;

begin

  main : process
  begin
    test_runner_setup(runner, runner_cfg);


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;