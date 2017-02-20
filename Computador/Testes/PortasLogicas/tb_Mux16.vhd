library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_Mux16 is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_Mux16 is

component Mux16 is
	port ( 
			a:   in  STD_LOGIC_VECTOR(15 downto 0);
			b:   in  STD_LOGIC_VECTOR(15 downto 0);
			sel: in  STD_LOGIC;
			q:   out STD_LOGIC_VECTOR(15 downto 0));
end component;

   signal  inA, inB, outQ : STD_LOGIC_VECTOR(15 downto 0);
   signal  inSel : STD_LOGIC;


begin

  mapping: Mux16 port map(inA, inB, inSel, outQ);

  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= "1111000011110000"; inB <= "0000000000000000";
      wait for 200 ps;
      assert(outQ = "0000111100001111")  report "Falha em teste: 1" severity error;


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;