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

   signal inA, inB, inC, inD, outQ : STD_LOGIC;
   signal inSel : STD_LOGIC_VECTOR(1 downto 0);

begin

	mapping: Mux4Way port map(inA, inB, inC, inD, inSel, outQ);

  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= '1'; inB <= '0'; inC <='0'; inD <='0'; inSel<= "00";
      wait for 200 ps;
      assert(outQ = '1')  report "Falha em teste: 1" severity error;

    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;