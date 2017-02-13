library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_Mux4Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_Mux4Way is

component Mux4Way is
	port ( 
			a:   in  STD_LOGIC;
			b:   in  STD_LOGIC;
			c:   in  STD_LOGIC;
			d:   in  STD_LOGIC;
			sel: in  STD_LOGIC_VECTOR(1 downto 0);
			q:   out STD_LOGIC);
end component;

begin

  main : process
  begin
    test_runner_setup(runner, runner_cfg);


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;