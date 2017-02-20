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

   signal inA, inB, inC, inD, inE, inF, inG, inH, outQ : STD_LOGIC;
   signal inSel : STD_LOGIC_VECTOR(2 downto 0);


begin

	mapping: Mux8Way port map(inA, inB, inC, inD, inE, inF, inG, inH, inSel, outQ);


  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= '1'; inB <= '0'; inC <='0'; inD <='0'; inE <= '0'; inF <= '0'; inG <='0'; inH<='0'; inSel<= "000";
      wait for 200 ps;
      assert(outQ = '1')  report "Falha em teste: 1" severity error;

    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;