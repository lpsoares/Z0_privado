library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_Mux8Way16 is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_Mux8Way16 is

component Mux8Way16 is
	port ( 
			a:   in  STD_LOGIC_VECTOR(15 downto 0);
			b:   in  STD_LOGIC_VECTOR(15 downto 0);
			c:   in  STD_LOGIC_VECTOR(15 downto 0);
			d:   in  STD_LOGIC_VECTOR(15 downto 0);
			e:   in  STD_LOGIC_VECTOR(15 downto 0);
			f:   in  STD_LOGIC_VECTOR(15 downto 0);
			g:   in  STD_LOGIC_VECTOR(15 downto 0);
			h:   in  STD_LOGIC_VECTOR(15 downto 0);
			sel: in  STD_LOGIC_VECTOR(2 downto 0);
			q:   out STD_LOGIC_VECTOR(15 downto 0));
end component;

   signal inA, inB, inC, inD, inE, inF, inG, inH, outQ : STD_LOGIC_VECTOR(15 downto 0);
   signal inSel : STD_LOGIC_VECTOR(2 downto 0);

begin

	mapping: Mux8Way16 port map(inA, inB, inC, inD, inE, inF, inG, inH, inSel, outQ);

  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= "1111111111111111"; inB <= "0000000000000000"; inC <="0000000000000000"; inD <="0000000000000000";
      inE <= "0000000000000000"; inF <= "0000000000000000"; inG <="0000000000000000"; inH<="0000000000000000"; inSel<= "000";
      wait for 200 ps;
      assert(outQ = "1111111111111111")  report "Falha em teste: 1" severity error;


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;