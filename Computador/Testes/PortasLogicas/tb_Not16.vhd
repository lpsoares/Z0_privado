-- Elementos de Sistemas
-- developed by Luciano Soares
-- file: tb_Not16.vhd
-- date: 4/4/2017

library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_Not16 is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_Not16 is

component Not16 is
	port ( 
			a:   in  STD_LOGIC_VECTOR(15 downto 0);
			q:   out STD_LOGIC_VECTOR(15 downto 0));
end component;

   signal  inA, outQ : STD_LOGIC_VECTOR(15 downto 0);

begin

  mapping: Not16 port map(inA, outQ);


  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= "1111000011110000";
      wait for 200 ps;
      assert(outQ = "0000111100001111")  report "Falha em teste: 1" severity error;

      -- Teste: 2
      inA <= "0000111100001111";
      wait for 200 ps;
      assert(outQ = "1111000011110000")  report "Falha em teste: 2" severity error;

      -- Teste: 3
      inA <= "0000000000000000";
      wait for 200 ps;
      assert(outQ = "1111111111111111")  report "Falha em teste: 3" severity error;

      -- Teste: 4
      inA <= "1111111111111111";
      wait for 200 ps;
      assert(outQ = "0000000000000000")  report "Falha em teste: 4" severity error;


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;