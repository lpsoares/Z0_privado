library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_Or8Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_Or8Way is

component Or8Way is
	port ( 
			a:   in  STD_LOGIC;
			b:   in  STD_LOGIC;
			c:   in  STD_LOGIC;
			d:   in  STD_LOGIC;
			e:   in  STD_LOGIC;
			f:   in  STD_LOGIC;
			g:   in  STD_LOGIC;
			h:   in  STD_LOGIC;
			q:   out STD_LOGIC);
end component;

   signal  inA, inB, inC, inD, inE, inF, inG, inH, outQ : STD_LOGIC;

begin

  mapping: Or8Way port map(inA, inB, inC, inD, inE, inF, inG, inH, outQ);


  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= '0'; inB <= '0'; inC <= '0'; inD <= '0'; inE <= '0'; inF <= '0'; inG <= '0'; inH <= '0';
      wait for 200 ps;
      assert(outQ = '0')  report "Falha em teste: 1" severity error;

      -- Teste: 2
      inA <= '0'; inB <= '0'; inC <= '0'; inD <= '0'; inE <= '0'; inF <= '0'; inG <= '0'; inH <= '1';
      wait for 200 ps;
      assert(outQ = '1')  report "Falha em teste: 2" severity error;

    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;
