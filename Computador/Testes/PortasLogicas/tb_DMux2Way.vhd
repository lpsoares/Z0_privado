library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_DMux2Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_DMux2Way is

component DMux2Way is
	port ( 
			a:   in  STD_LOGIC;
			sel: in  STD_LOGIC;
			q0:  out STD_LOGIC;
			q1:  out STD_LOGIC);
end component;

   signal  inA, inSel, outQ0, outQ1 : STD_LOGIC;

begin

	mapping: DMux2Way port map(inA, inSel, outQ0, outQ1);

  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= '1'; inSel<= '0';
      wait for 200 ps;
      assert(outQ0 = '1' and outQ1 = '0')  report "Falha em teste: 1" severity error;

      -- Teste: 2
      inA <= '1'; inSel<= '1';
      wait for 200 ps;
      assert(outQ0 = '0' and outQ1 = '1')  report "Falha em teste: 2" severity error;

      -- Teste: 3
      inA <= '0'; inSel<= '0';
      wait for 200 ps;
      assert(outQ0 = '0' and outQ1 = '0')  report "Falha em teste: 3" severity error;

      -- Teste: 4
      inA <= '0'; inSel<= '1';
      wait for 200 ps;
      assert(outQ0 = '0' and outQ1 = '0')  report "Falha em teste: 4" severity error;


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;