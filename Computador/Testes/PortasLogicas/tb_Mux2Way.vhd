library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_Mux2Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_Mux2Way is

component Mux2Way is
	port ( 
			a:   in  STD_LOGIC;
			b:   in  STD_LOGIC;
			sel: in  STD_LOGIC;
			q:   out STD_LOGIC);
end component;

   signal  inA, inB, inSel, outQ : STD_LOGIC;

begin

	mapping: Mux2Way port map(inA, inB, inSel, outQ);

  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= '1'; inB <= '0'; inSel<= '0';
      wait for 200 ps;
      assert(outQ = '1')  report "Falha em teste: 1" severity error;

    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;