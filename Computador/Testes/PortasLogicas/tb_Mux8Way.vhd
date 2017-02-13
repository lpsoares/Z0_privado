library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_Mux8Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_Mux8Way is

component Mux8Way is
	port ( 
			a:   in  STD_LOGIC;
			b:   in  STD_LOGIC;
			c:   in  STD_LOGIC;
			d:   in  STD_LOGIC;
			e:   in  STD_LOGIC;
			f:   in  STD_LOGIC;
			g:   in  STD_LOGIC;
			h:   in  STD_LOGIC;
			sel: in  STD_LOGIC_VECTOR(2 downto 0);
			q:   out STD_LOGIC);
end component;


begin

  main : process
  begin
    test_runner_setup(runner, runner_cfg);


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;